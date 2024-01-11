package com.example.password_manager.export_import

import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R
import com.example.password_manager.db.PasswordDbHelper
import com.example.password_manager.db.PasswordDbManager

class ExportActivity:AppCompatActivity() {
    private val passwordDbManager = PasswordDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)
        val exportButton: Button = findViewById(R.id.exportDbButton)
        val cancelButton: Button = findViewById(R.id.cancelExportButton)
        val dbNameEditText: EditText = findViewById(R.id.exportDbNameEditText)
        exportButton.setOnClickListener{
            val dbName = dbNameEditText.text.toString()
            val dbHelper = PasswordDbHelper(this)
            val path = "${ContextWrapper(this).filesDir.parent}/databases/"
            val fulPath = path + dbName + ".db"
            passwordDbManager.decryptWholeTable()
            dbHelper.exportDataBase(this,fulPath)
            passwordDbManager.encryptWholeTable()
            finish()

        }
        cancelButton.setOnClickListener{
            finish()
        }
    }
}