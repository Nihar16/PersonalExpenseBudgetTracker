package com.example.personalexpense_budgettracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
