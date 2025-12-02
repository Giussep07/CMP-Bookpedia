package dev.giussepr.bookpedia.book.presentation.book_list

import dev.giussepr.bookpedia.book.domain.Book
import dev.giussepr.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = (1..100).map {
        Book(
            id = it.toString(),
            title = "Book $it",
            imageUrl = "https://test.com",
            authors = listOf("Giussep Ricardo"),
            description = "Description of $it",
            languages = emptyList(),
            firstPublishYear = null,
            averageRating = 4.67854,
            ratingCount = 5,
            numPages = 100,
            numEditions = 7
        )
    },
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
