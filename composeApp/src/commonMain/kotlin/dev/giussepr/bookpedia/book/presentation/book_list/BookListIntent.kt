package dev.giussepr.bookpedia.book.presentation.book_list

import dev.giussepr.bookpedia.book.domain.Book

sealed interface BookListIntent {
    data class OnSearchQueryChange(val query: String) : BookListIntent
    data class OnBookClick(val book: Book) : BookListIntent
    data class OnTabSelected(val index: Int) : BookListIntent
}
