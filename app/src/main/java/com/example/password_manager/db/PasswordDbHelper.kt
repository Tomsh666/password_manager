package com.example.password_manager.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PasswordDbHelper(context: Context) : SQLiteOpenHelper(context, PasswordDbClass.DATABASE_NAME,
    null, PasswordDbClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PasswordDbClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PasswordDbClass.SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun importDataBase(context: Context, tempDBPath: String) {
        val currentDBPath = context.getDatabasePath(PasswordDbClass.DATABASE_NAME).absolutePath
        close()
        copyDataBase(tempDBPath, currentDBPath)
        writableDatabase.close()
    }

    fun exportDataBase(context: Context, tempDBPath: String) {
        val currentDBPath = context.getDatabasePath(PasswordDbClass.DATABASE_NAME).absolutePath
        copyDataBase(currentDBPath, tempDBPath)
    }

    private fun copyDataBase( inputDBPath: String, outputDBPath: String) {
        try {
            val input = FileInputStream(inputDBPath)
            val output = FileOutputStream(outputDBPath)
            val buffer = ByteArray(1024)
            var length: Int
            while (input.read(buffer).also { length = it } > 0) {
                output.write(buffer, 0, length)
            }
            output.flush()
            output.close()
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}