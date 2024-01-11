package com.example.password_manager.secret_code

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class SignUpActivity:AppCompatActivity() {
    private val fileHelper = FileHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val button: Button = findViewById(R.id.save_button)
        val editTextCode: EditText = findViewById(R.id.pass_text)
        button.setOnClickListener{
            val enteredCode = editTextCode.text.toString()
            fileHelper.savePasswordToFile(enteredCode)
            MyApp.secretKey = fileHelper.readFromExternalFile()
            while (MyApp.secretKey.length < 16) {
                MyApp.secretKey += '0'
            }
            MyApp.isInitialized = true
            finish()
        }
    }
}
