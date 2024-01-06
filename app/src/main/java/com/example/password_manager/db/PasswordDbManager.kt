package com.example.password_manager.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.provider.BaseColumns

class PasswordDbManager(val context: Context) {
    val passwordDbHelper = PasswordDbHelper(context)
    var db:SQLiteDatabase? = null

    fun openDb(){
        db = passwordDbHelper.writableDatabase
    }

    fun insertToDb(resource: String, login: String, pass: String, note: String){
        val values = ContentValues().apply {
            put(PasswordDbClass.COLUMN_RESOURCE, resource)
            put(PasswordDbClass.COLUMN_LOGIN, login)
            put(PasswordDbClass.COLUMN_PASS, pass)
            put(PasswordDbClass.COLUMN_NOTE, note)
        }
        db?.insert(PasswordDbClass.TABLE_NAME, null, values)
    }

    fun updatePassword(id: Int, newResource: String, newLogin: String, newPassword: String, newNote: String){
        val values = ContentValues().apply {
            put(PasswordDbClass.COLUMN_RESOURCE, newResource)
            put(PasswordDbClass.COLUMN_LOGIN, newLogin)
            put(PasswordDbClass.COLUMN_PASS, newPassword)
            put(PasswordDbClass.COLUMN_NOTE, newNote)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        db?.update(PasswordDbClass.TABLE_NAME, values, selection, selectionArgs)
    }

    fun getInfoFromId(id: Int): Array<String>?{
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db?.query(PasswordDbClass.TABLE_NAME,null,selection,selectionArgs,null,null,null)
        var dataArray: Array<String>? = null
        with(cursor){
            while (this?.moveToNext()!!){
                val resourceData = getString(getColumnIndexOrThrow(PasswordDbClass.COLUMN_RESOURCE))
                val loginData = getString(getColumnIndexOrThrow(PasswordDbClass.COLUMN_LOGIN))
                val passwordData = getString(getColumnIndexOrThrow(PasswordDbClass.COLUMN_PASS))
                val noteData = getString(getColumnIndexOrThrow(PasswordDbClass.COLUMN_NOTE))
                dataArray = arrayOf(resourceData, loginData, passwordData, noteData)
            }
        }
        cursor?.close()
        return dataArray
    }

    fun deletePassword(id: Int) {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        db?.delete(PasswordDbClass.TABLE_NAME, selection, selectionArgs)
    }

    fun readDbData() : ArrayList<String>{
        val dataList = ArrayList<String>()
        val cursor = db?.query(PasswordDbClass.TABLE_NAME,null,null,null,null,null,null)

        with(cursor){
            while (this?.moveToNext()!!){
                val resourceData = getString(getColumnIndexOrThrow(PasswordDbClass.COLUMN_RESOURCE))
                dataList.add(resourceData)
            }
        }
        cursor?.close()
        return dataList
    }

    fun closeDb(){
        passwordDbHelper.close()
    }
}