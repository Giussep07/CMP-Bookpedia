package dev.giussepr.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.giussepr.bookpedia.book.presentation.book_list.BookListRoot
import dev.giussepr.bookpedia.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = koinViewModel<BookListViewModel>()
        BookListRoot(
            viewModel = viewModel,
            onBookClick = {}
        )
    }
}
