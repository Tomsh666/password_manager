package com.example.password_manager.db

import android.provider.BaseColumns

object PasswordDbClass {
    const val TABLE_NAME = "passwords"
    const val COLUMN_RESOURCE = "resource"
    const val COLUMN_LOGIN = "login"
    const val COLUMN_PASS = "pass"
    const val COLUMN_NOTE = "note"

    const val DATABASE_VERSION = 3
    const val DATABASE_NAME = "password_manager.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_RESOURCE TEXT," +
            "$COLUMN_LOGIN TEXT," +
            "$COLUMN_PASS TEXT," +
            "$COLUMN_NOTE TEXT)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}