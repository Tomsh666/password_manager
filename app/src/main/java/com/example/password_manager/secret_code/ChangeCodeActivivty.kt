package com.example.password_manager.secret_code

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.R
import com.example.password_manager.db.PasswordDbManager

class ChangeCodeActivivty: AppCompatActivity() {
    private val fileHelper = FileHelper(this)
    private val passwordDbManager = PasswordDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_secret_code)
        val saveButton : Button = findViewById(R.id.save_secret_code_button)
        val cancelButton: Button = findViewById(R.id.cancel_button2)
        val showCodeCheckBox: CheckBox = findViewById(R.id.show_code_checkBox)
        val oldCodeEditText: EditText = findViewById(R.id.old_code_editText)
        val newCodeEditText: EditText = findViewById(R.id.new_code_editText)
        oldCodeEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        newCodeEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        saveButton.setOnClickListener{
            changeCode()
        }
        cancelButton.setOnClickListener{
            finish()
        }
        showCodeCheckBox.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked){
                oldCodeEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                newCodeEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else {
                oldCodeEditText.transformationMethod = PasswordTransformationMethod.getInstance()
                newCodeEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
    private fun changeCode(){
        val oldCodeEditText: EditText = findViewById(R.id.old_code_editText)
        val newCodeEditText: EditText = findViewById(R.id.new_code_editText)
        val oldCode = oldCodeEditText.text.toString()
        val newCode = newCodeEditText.text.toString()
        val codeValue = fileHelper.readFromExternalFile()
        if (oldCode == codeValue){
            passwordDbManager.decryptWholeTable()
            MyApp.secretKey = newCode
            fileHelper.savePasswordToFile(newCode)
            while (MyApp.secretKey.length < 16) {
                MyApp.secretKey += '0'
            }
            passwordDbManager.encryptWholeTable()
            finish()
        }
        else{
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
            .setMessage("Wrong old secret code")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}