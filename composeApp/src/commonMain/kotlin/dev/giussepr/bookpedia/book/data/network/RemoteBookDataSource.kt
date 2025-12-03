package dev.giussepr.bookpedia.book.data.network

import dev.giussepr.bookpedia.book.data.dto.SearchResponseDto
import dev.giussepr.bookpedia.core.domain.DataError
import dev.giussepr.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultsLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
}
