package com.example.password_manager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PassCreationActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_creation)
        val SavePassButton : Button = findViewById(R.id.save_pass_button)
        val CancelButton : Button = findViewById(R.id.cancel_button)
        val ShowPassCheckBox: CheckBox = findViewById(R.id.show_pass_checkBox)
        val PassEditText: EditText = findViewById(R.id.password_editText)
        val CopyButton : Button = findViewById(R.id.copy_button)
        PassEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        SavePassButton.setOnClickListener{
            finish()
        }
        CancelButton.setOnClickListener{
            finish()
        }
        CopyButton.setOnClickListener{
            val textToCopy = PassEditText.text.toString()
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(this, "Сopied to clipboard", Toast.LENGTH_SHORT).show()
        }
        ShowPassCheckBox.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked){
                PassEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else {
                PassEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }
}