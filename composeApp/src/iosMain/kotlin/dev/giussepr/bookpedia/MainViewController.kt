package dev.giussepr.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import dev.giussepr.bookpedia.app.App
import dev.giussepr.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
