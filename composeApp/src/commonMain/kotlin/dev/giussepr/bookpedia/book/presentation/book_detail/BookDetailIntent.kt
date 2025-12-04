package dev.giussepr.bookpedia.book.presentation.book_detail

import dev.giussepr.bookpedia.book.domain.Book

sealed interface BookDetailIntent {
    data object OnBackClick : BookDetailIntent
    data object OnFavoriteClick : BookDetailIntent
    data class OnSelectedBookChange(val book: Book) : BookDetailIntent
}
