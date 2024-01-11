package com.example.password_manager.secret_code

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class LoginActivity: AppCompatActivity() {
    private val fileHelper = FileHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val codeValue = fileHelper.readFromExternalFile()
        val button: Button = findViewById(R.id.login_button)
        val editTextCode: EditText = findViewById(R.id.pass_text)
        button.setOnClickListener{
            val enteredCode = editTextCode.text.toString()
            if (enteredCode == codeValue){
                MyApp.secretKey = codeValue
                while (MyApp.secretKey.length < 16) {
                    MyApp.secretKey += "0"
                }
                MyApp.isInitialized = true
                finish()
            }
            else{
                showAlertDialog()
            }

        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
            .setMessage("Wrong secret code")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
