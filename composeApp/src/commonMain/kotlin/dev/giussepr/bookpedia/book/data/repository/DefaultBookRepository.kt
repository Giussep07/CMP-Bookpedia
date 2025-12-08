package dev.giussepr.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import dev.giussepr.bookpedia.book.data.database.FavoritesBookDao
import dev.giussepr.bookpedia.book.data.mappers.toBook
import dev.giussepr.bookpedia.book.data.mappers.toBookEntity
import dev.giussepr.bookpedia.book.data.network.RemoteBookDataSource
import dev.giussepr.bookpedia.book.domain.Book
import dev.giussepr.bookpedia.book.domain.BookRepository
import dev.giussepr.bookpedia.core.domain.DataError
import dev.giussepr.bookpedia.core.domain.EmptyResult
import dev.giussepr.bookpedia.core.domain.Result
import dev.giussepr.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoritesBookDao: FavoritesBookDao
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        // Check if the book exists in DB
        val localResult = favoritesBookDao.getFavoriteBook(bookId)

        return if (localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoritesBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoritesBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoritesBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            e.printStackTrace()
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoritesBookDao.deleteFavoriteBook(id)
    }
}
