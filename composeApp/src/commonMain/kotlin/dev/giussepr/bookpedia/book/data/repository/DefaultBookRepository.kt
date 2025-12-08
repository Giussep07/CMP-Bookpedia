package dev.giussepr.bookpedia.book.data.repository

import dev.giussepr.bookpedia.book.data.mappers.toBook
import dev.giussepr.bookpedia.book.data.network.RemoteBookDataSource
import dev.giussepr.bookpedia.book.domain.Book
import dev.giussepr.bookpedia.book.domain.BookRepository
import dev.giussepr.bookpedia.core.domain.DataError
import dev.giussepr.bookpedia.core.domain.Result
import dev.giussepr.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        return remoteBookDataSource
            .getBookDetails(bookId)
            .map { it.description }
    }
}
