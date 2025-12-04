package dev.giussepr.bookpedia.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun onIntent(intent: BookDetailIntent) {
        when (intent) {
            BookDetailIntent.OnFavoriteClick -> {

            }

            is BookDetailIntent.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = intent.book
                    )
                }
            }

            else -> Unit
        }
    }
}