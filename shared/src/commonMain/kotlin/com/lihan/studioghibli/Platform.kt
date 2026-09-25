package com.lihan.studioghibli

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform