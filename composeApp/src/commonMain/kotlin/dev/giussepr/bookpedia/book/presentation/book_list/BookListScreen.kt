package dev.giussepr.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import dev.giussepr.bookpedia.book.domain.Book
import dev.giussepr.bookpedia.book.presentation.book_list.components.BookSearchBar
import dev.giussepr.bookpedia.core.presentation.DarkBlue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    BookListScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                is BookListIntent.OnBookClick -> onBookClick(intent.book)
                else -> Unit
            }
            viewModel.onIntent(intent)
        }
    )
}

@Composable
private fun BookListScreen(
    state: BookListState,
    onIntent: (BookListIntent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onIntent(BookListIntent.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun BookListScreenPreview() {
    val books = (1..100).map {
        Book(
            id = it.toString(),
            title = "Book $it",
            imageUrl = "https://test.com",
            authors = listOf("Giussep Ricardo"),
            description = "Description of $it",
            languages = emptyList(),
            firstPublishYear = null,
            averageRating = 4.67854,
            ratingCount = 5,
            numPages = 100,
            numEditions = 7
        )
    }

    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onIntent = {}
    )
}
