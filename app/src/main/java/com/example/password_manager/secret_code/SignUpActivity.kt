package com.example.password_manager.secret_code

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R

class SignUpActivity:AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        sharedPreferences = getSharedPreferences("password_manager",Context.MODE_PRIVATE)
        Log.d("Tag","$sharedPreferences")
        val button: Button = findViewById(R.id.save_button)
        val editTextCode: EditText = findViewById(R.id.pass_text)
        button.setOnClickListener{
            val enteredCode = editTextCode.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString("code", enteredCode)
            editor.putBoolean("isCodeSet", true)
            editor.apply()
            finish()
        }
    }
}
