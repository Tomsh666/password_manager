package com.example.password_manager.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PasswordDbHelper(context: Context) : SQLiteOpenHelper(context, PasswordDbClass.DATABASE_NAME,
    null, PasswordDbClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PasswordDbClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PasswordDbClass.SQL_DELETE_TABLE)
        onCreate(db)
    }


}