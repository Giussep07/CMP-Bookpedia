@file:OptIn(FlowPreview::class)

package dev.giussepr.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.giussepr.bookpedia.book.domain.Book
import dev.giussepr.bookpedia.book.domain.BookRepository
import dev.giussepr.bookpedia.core.domain.onError
import dev.giussepr.bookpedia.core.domain.onSuccess
import dev.giussepr.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private var cachedBooks: List<Book> = emptyList()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onIntent(intent: BookListIntent) {
        when (intent) {
            is BookListIntent.OnBookClick -> TODO()
            is BookListIntent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = intent.query
                    )
                }
            }

            is BookListIntent.OnTabSelected -> {
                _state.update {
                    it.copy(
                        selectedTabIndex = intent.index
                    )
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) =
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            repository
                .searchBooks(query)
                .onSuccess { searchResults ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            searchResults = searchResults
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            searchResults = emptyList(),
                            isLoading = false,
                            errorMessage = error.toUiText()
                        )
                    }
                }
        }
}
