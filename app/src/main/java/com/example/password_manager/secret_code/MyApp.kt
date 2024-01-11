package com.example.password_manager.secret_code

import android.app.Application

class MyApp : Application() {
    companion object {
        lateinit var secretKey: String
        var isInitialized: Boolean = false
    }
}