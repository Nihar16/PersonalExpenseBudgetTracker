package com.example.personalexpense_budgettracker

class WasmPlatform : Platform {
    override val name: String = "Web"
}

actual fun getPlatform(): Platform = WasmPlatform()
