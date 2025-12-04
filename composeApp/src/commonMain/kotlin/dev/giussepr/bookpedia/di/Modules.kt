package dev.giussepr.bookpedia.di

import dev.giussepr.bookpedia.book.data.network.KtorRemoteBookDataSource
import dev.giussepr.bookpedia.book.data.network.RemoteBookDataSource
import dev.giussepr.bookpedia.book.data.repository.DefaultBookRepository
import dev.giussepr.bookpedia.book.domain.BookRepository
import dev.giussepr.bookpedia.book.presentation.SelectedBookViewModel
import dev.giussepr.bookpedia.book.presentation.book_detail.BookDetailViewModel
import dev.giussepr.bookpedia.book.presentation.book_list.BookListViewModel
import dev.giussepr.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}