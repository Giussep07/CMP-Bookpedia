package dev.giussepr.bookpedia.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1
)
@ConstructedBy(BookDatabaseConstructor::class)
@TypeConverters(
    StringListTypeConverter::class
)
abstract class FavoritesBookDatabase : RoomDatabase() {
    abstract val favoritesBookDao: FavoritesBookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}
