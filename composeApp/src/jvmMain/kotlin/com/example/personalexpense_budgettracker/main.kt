package com.example.personalexpense_budgettracker

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "PersonalExpenseBudgetTracker",
    ) {
        App()
    }
}