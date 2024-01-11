package com.example.password_manager.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.password_manager.decrypt
import com.example.password_manager.encrypt

class PasswordDbManager(val context: Context) {
    val passwordDbHelper = PasswordDbHelper(context)
    private var db:SQLiteDatabase? = null

    fun openDb(){
        db = passwordDbHelper.writableDatabase
    }

    fun insertToDb(resource: String, login: String, pass: String, note: String){
        val values = ContentValues().apply {
            put(PasswordDbClass.COLUMN_RESOURCE, resource.encrypt())
            put(PasswordDbClass.COLUMN_LOGIN, login.encrypt())
            put(PasswordDbClass.COLUMN_PASS, pass.encrypt())
            put(PasswordDbClass.COLUMN_NOTE, note.encrypt())
        }
        db?.insert(PasswordDbClass.TABLE_NAME, null, values)
    }

    fun updatePassword(id: Int, newResource: String, newLogin: String, newPassword: String, newNote: String){
        val values = ContentValues().apply {
            put(PasswordDbClass.COLUMN_RESOURCE, newResource.encrypt())
            put(PasswordDbClass.COLUMN_LOGIN, newLogin.encrypt())
            put(PasswordDbClass.COLUMN_PASS, newPassword.encrypt())
            put(PasswordDbClass.COLUMN_NOTE, newNote.encrypt())
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
                dataArray = arrayOf(
                    resourceData.decrypt(),
                    loginData.decrypt(),
                    passwordData.decrypt(),
                    noteData.decrypt()
                )
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

    fun decryptWholeTable() {
        val dbHelper = PasswordDbHelper(context)
        val writableDatabase = dbHelper.writableDatabase
        val readableDatabase = dbHelper.readableDatabase
        val cursor = readableDatabase.query(
            PasswordDbClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val resource = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_RESOURCE))
                val login = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_LOGIN))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_PASS))
                val note = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_NOTE))
                val decryptedResource = resource.decrypt()
                val decryptedLogin = login.decrypt()
                val decryptedPassword = password.decrypt()
                val decryptedNote = note.decrypt()
                val values = ContentValues().apply {
                    put(PasswordDbClass.COLUMN_RESOURCE, decryptedResource)
                    put(PasswordDbClass.COLUMN_LOGIN, decryptedLogin)
                    put(PasswordDbClass.COLUMN_PASS, decryptedPassword)
                    put(PasswordDbClass.COLUMN_NOTE, decryptedNote)
                }
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf(id.toString())
                writableDatabase.update(PasswordDbClass.TABLE_NAME, values, selection, selectionArgs)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    fun encryptWholeTable() {
        val dbHelper = PasswordDbHelper(context)
        val writableDatabase = dbHelper.writableDatabase
        val readableDatabase = dbHelper.readableDatabase
        val cursor = readableDatabase.query(
            PasswordDbClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val resource = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_RESOURCE))
                val login = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_LOGIN))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_PASS))
                val note = cursor.getString(cursor.getColumnIndexOrThrow(PasswordDbClass.COLUMN_NOTE))
                val decryptedResource = resource.encrypt()
                val decryptedLogin = login.encrypt()
                val decryptedPassword = password.encrypt()
                val decryptedNote = note.encrypt()
                val values = ContentValues().apply {
                    put(PasswordDbClass.COLUMN_RESOURCE, decryptedResource)
                    put(PasswordDbClass.COLUMN_LOGIN, decryptedLogin)
                    put(PasswordDbClass.COLUMN_PASS, decryptedPassword)
                    put(PasswordDbClass.COLUMN_NOTE, decryptedNote)
                }
                val selection = "${BaseColumns._ID} = ?"
                val selectionArgs = arrayOf(id.toString())
                writableDatabase.update(PasswordDbClass.TABLE_NAME, values, selection, selectionArgs)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }


    fun closeDb(){
        passwordDbHelper.close()
    }
}