package dev.giussepr.bookpedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform