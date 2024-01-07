package com.example.password_manager.export_import

import android.content.ContextWrapper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R
import com.example.password_manager.db.PasswordDbHelper
import java.io.File

class ImportActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)
        val importButton: Button = findViewById(R.id.importDbButton)
        val cancelButton: Button = findViewById(R.id.cancelImportButton)
        val dbNameEditText: EditText = findViewById(R.id.importDbNameEditText)
        importButton.setOnClickListener{
            val dbName = dbNameEditText.text.toString()
            val dbHelper = PasswordDbHelper(this)
            val path = "${ContextWrapper(this).filesDir.parent}/databases/"
            val fulPath = path + dbName + ".db"
            if (isFileExist(fulPath)){
                dbHelper.importDataBase(this,fulPath)
                finish()
            }
            else{
                showAlertDialog()
            }
        }
        cancelButton.setOnClickListener{
            finish()
        }
    }

    private fun isFileExist(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists()
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
            .setMessage("Wrong file name")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}