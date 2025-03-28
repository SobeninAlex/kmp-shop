package org.example.kmp_shop

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform