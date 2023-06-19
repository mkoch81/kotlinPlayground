package com.example.playground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform