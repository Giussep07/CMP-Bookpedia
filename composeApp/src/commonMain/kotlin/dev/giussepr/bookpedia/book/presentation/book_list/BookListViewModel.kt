package dev.giussepr.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import dev.giussepr.bookpedia.book.domain.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel(
    private val repository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

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

}
