package dev.giussepr.bookpedia.book.domain

import dev.giussepr.bookpedia.core.domain.DataError
import dev.giussepr.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
}
