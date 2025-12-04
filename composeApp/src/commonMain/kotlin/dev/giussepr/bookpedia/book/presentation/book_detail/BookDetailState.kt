package dev.giussepr.bookpedia.book.presentation.book_detail

import dev.giussepr.bookpedia.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
