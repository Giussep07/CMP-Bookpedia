package dev.giussepr.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.giussepr.bookpedia.book.data.network.KtorRemoteBookDataSource
import dev.giussepr.bookpedia.book.data.repository.DefaultBookRepository
import dev.giussepr.bookpedia.book.presentation.book_list.BookListRoot
import dev.giussepr.bookpedia.book.presentation.book_list.BookListViewModel
import dev.giussepr.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    MaterialTheme {
        BookListRoot(
            viewModel = remember {
                BookListViewModel(
                    repository = DefaultBookRepository(
                        remoteBookDataSource = KtorRemoteBookDataSource(
                            httpClient = HttpClientFactory.create(
                                engine = engine
                            )
                        )
                    )
                )
            },
            onBookClick = {}
        )
    }
}
