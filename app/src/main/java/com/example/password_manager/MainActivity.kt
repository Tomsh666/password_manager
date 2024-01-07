package com.example.password_manager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.password_manager.db.PasswordDbManager
import com.example.password_manager.export_import.ExportActivity
import com.example.password_manager.export_import.ImportActivity
import com.example.password_manager.secret_code.ChangeCodeActivivty
import com.example.password_manager.secret_code.LoginActivity
import com.example.password_manager.secret_code.SignUpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    val passwordDbManager = PasswordDbManager(this)

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
        val dataList = passwordDbManager.readDbData()
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,dataList)
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
            val intent = Intent(this, PassChangingActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val dataList = passwordDbManager.readDbData()
        val passTable : ListView = findViewById(R.id.pass_table)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,dataList)
        passTable.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        passwordDbManager.closeDb()
    }
}