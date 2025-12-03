package dev.giussepr.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.giussepr.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookpedia",
        ) {
            App()
        }
    }
}