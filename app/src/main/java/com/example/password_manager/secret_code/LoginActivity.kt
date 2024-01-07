package com.example.password_manager.secret_code

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R

class LoginActivity: AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences("password_manager",Context.MODE_PRIVATE)
        val button: Button = findViewById(R.id.login_button)
        val editTextCode: EditText = findViewById(R.id.pass_text)
        button.setOnClickListener{
            val enteredCode = editTextCode.text.toString()
            val codeValue = sharedPreferences.getString("code", "")
            if (enteredCode == codeValue){
//                val editor = sharedPreferences.edit()
//                editor.clear()
//                editor.apply()
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
