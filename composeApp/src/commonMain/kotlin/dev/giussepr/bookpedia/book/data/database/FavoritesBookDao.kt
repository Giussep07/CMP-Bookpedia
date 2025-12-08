package dev.giussepr.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesBookDao {

    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BOOKENTITY WHERE id=:id")
    suspend fun getFavoriteBook(id: String): BookEntity?

    @Query("DELETE FROM BOOKENTITY WHERE id=:id")
    suspend fun deleteFavoriteBook(id: String)
}
