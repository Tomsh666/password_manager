package com.example.password_manager.secret_code

import android.content.Context
import android.content.ContextWrapper
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileHelper(private val context: Context) {
    fun savePasswordToFile(password: String) {
        try {
            val path = "${ContextWrapper(context).filesDir.parent}/files/"
            val fileName = "SecretKey.txt"
            val file = File(path, fileName)
            val fileOutputStream = FileOutputStream(file)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            outputStreamWriter.write(password)
            outputStreamWriter.close()
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun readFromExternalFile(): String {
        val stringBuilder = StringBuilder()
        try {
            val path = "${ContextWrapper(context).filesDir.parent}/files/"
            val file = File(path, "SecretKey.txt")
            val fileInputStream = FileInputStream(file)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bufferedReader.close()
            inputStreamReader.close()
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    fun isFileEmpty(): Boolean {
        val path = "${ContextWrapper(context).filesDir.parent}/files/"
        val file = File(path, "SecretKey.txt")
        return file.length() == 0L
    }
}