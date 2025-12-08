package dev.giussepr.bookpedia.book.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavoritesBookDatabase> {
    override fun initialize(): FavoritesBookDatabase
}
