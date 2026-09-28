package com.kukurodev.minddrop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform