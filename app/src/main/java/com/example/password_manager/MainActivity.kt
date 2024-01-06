package com.example.password_manager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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
        val PassAddButton : Button = findViewById(R.id.pass_add_button)
        val PassImportButton : Button = findViewById(R.id.pass_import_button)
        val PassExportButton : Button = findViewById(R.id.pass_export_button)
        PassAddButton.setOnClickListener{
            startActivity(Intent(this, PassCreationActivity::class.java))
        }
        PassImportButton.setOnClickListener{
        }
        PassExportButton.setOnClickListener{

        }
    }
}