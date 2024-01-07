package com.example.password_manager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.ListView
import com.example.password_manager.db.PasswordDbClass
import com.example.password_manager.db.PasswordDbHelper
import com.example.password_manager.db.PasswordDbManager
import com.example.password_manager.export_import.ExportActivity
import com.example.password_manager.export_import.ImportActivity
import com.example.password_manager.secret_code.ChangeCodeActivivty
import com.example.password_manager.secret_code.LoginActivity
import com.example.password_manager.secret_code.SignUpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: PasswordCursorAdapter
    private val passwordDbManager = PasswordDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passwordDbManager.openDb()
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
        val ChangeCodeButton: Button = findViewById(R.id.change_code_button)
        val passTable : ListView = findViewById(R.id.pass_table)
        adapter = tableAdapter()
        passTable.adapter = adapter
        PassAddButton.setOnClickListener{
            passwordDbManager.closeDb()
            startActivity(Intent(this, PassCreationActivity::class.java))
            passwordDbManager.openDb()
        }
        PassImportButton.setOnClickListener{
            passwordDbManager.closeDb()
            startActivity(Intent(this, ImportActivity::class.java))
            passwordDbManager.openDb()
        }
        PassExportButton.setOnClickListener{
            passwordDbManager.closeDb()
            startActivity(Intent(this, ExportActivity::class.java))
            passwordDbManager.openDb()

        }
        ChangeCodeButton.setOnClickListener{
            startActivity(Intent(this, ChangeCodeActivivty::class.java))
        }
        passTable.setOnItemClickListener { _, _, position, _ ->
            val cursor = adapter.cursor
            if (cursor != null && cursor.moveToPosition(position)) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val intent = Intent(this, PassChangingActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.changeCursor(getUpdatedCursor())
    }

    override fun onDestroy() {
        super.onDestroy()
        passwordDbManager.closeDb()
    }

    private fun tableAdapter(): PasswordCursorAdapter {
        val dbHelper = PasswordDbHelper(this)
        val cursor = dbHelper.readableDatabase.query(
            PasswordDbClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        return PasswordCursorAdapter(this, cursor)
    }

    private fun getUpdatedCursor(): Cursor {
        val dbHelper = PasswordDbHelper(this)
        return dbHelper.readableDatabase.query(
            PasswordDbClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}