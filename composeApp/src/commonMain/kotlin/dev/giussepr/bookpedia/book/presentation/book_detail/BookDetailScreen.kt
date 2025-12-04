package dev.giussepr.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.description_unavailable
import bookpedia.composeapp.generated.resources.languages
import bookpedia.composeapp.generated.resources.pages
import bookpedia.composeapp.generated.resources.rating
import bookpedia.composeapp.generated.resources.synopsis
import dev.giussepr.bookpedia.book.presentation.book_detail.components.BlurredImageBackground
import dev.giussepr.bookpedia.book.presentation.book_detail.components.BookChip
import dev.giussepr.bookpedia.book.presentation.book_detail.components.BookChipSize
import dev.giussepr.bookpedia.book.presentation.book_detail.components.TitleContent
import dev.giussepr.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                is BookDetailIntent.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onIntent(intent)
        }
    )
}

@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    onIntent: (BookDetailIntent) -> Unit
) {
    BlurredImageBackground(
        imageUrl = state.book?.imageUrl,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onIntent(BookDetailIntent.OnFavoriteClick)
        },
        onBackClick = {
            onIntent(BookDetailIntent.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.book != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = state.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                // Authors
                Text(
                    text = state.book.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                // Rating & Pages
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.book.averageRating?.let { rating ->
                        TitleContent(
                            title = stringResource(Res.string.rating)
                        ) {
                            BookChip {
                                Text(
                                    text = "${round(rating * 10) / 10.0}"
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow
                                )
                            }
                        }
                    }

                    state.book.numPages?.let { pageCount ->
                        TitleContent(
                            title = stringResource(Res.string.pages)
                        ) {
                            BookChip {
                                Text(
                                    text = pageCount.toString()
                                )
                            }
                        }
                    }
                }

                // Languages
                if (state.book.languages.isNotEmpty()) {
                    TitleContent(
                        title = stringResource(Res.string.languages),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            state.book.languages.forEach { language ->
                                BookChip(
                                    size = BookChipSize.SMALL,
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = language.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )

                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = if (state.book.description.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable)
                        } else {
                            state.book.description
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.book.description.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f)
                        } else Color.Black,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BookDetailScreenPreview() {
    MaterialTheme {
        BookDetailScreen(
            state = BookDetailState(),
            onIntent = {}
        )
    }
}