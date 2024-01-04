package com.example.password_manager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("password_manager",Context.MODE_PRIVATE)
        val isCodeSet = sharedPreferences.getBoolean("isCodeSet", false)
        if (!isCodeSet) {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        setContentView(R.layout.activity_main)
    }
}