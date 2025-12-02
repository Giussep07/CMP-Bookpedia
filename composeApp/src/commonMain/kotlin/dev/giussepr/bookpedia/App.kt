package dev.giussepr.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.giussepr.bookpedia.book.presentation.book_list.BookListRoot
import dev.giussepr.bookpedia.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        BookListRoot(
            viewModel = remember { BookListViewModel() },
            onBookClick = {}
        )
    }
}
