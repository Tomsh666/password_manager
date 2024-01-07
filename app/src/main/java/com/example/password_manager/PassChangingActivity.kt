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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.password_manager.db.PasswordDbManager

class PassChangingActivity:AppCompatActivity(){
    private val passwordDbManager = PasswordDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_changing)
        setText()
        val ChangePassButton : Button = findViewById(R.id.change_pass_button)
        val CancelButton : Button = findViewById(R.id.cancel_button)
        val DeleteButton : Button = findViewById(R.id.delete_button)
        val ShowPassCheckBox: CheckBox = findViewById(R.id.show_pass_checkBox)
        val PassEditText: EditText = findViewById(R.id.password_editText)
        val CopyButton : Button = findViewById(R.id.copy_button)
        PassEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        ChangePassButton.setOnClickListener{
            passwordDbManager.openDb()
            savePass()
            passwordDbManager.closeDb()
            finish()
        }
        CancelButton.setOnClickListener{
            finish()
        }
        DeleteButton.setOnClickListener{
            passwordDbManager.openDb()
            val id = intent.getIntExtra("id", -1)
            passwordDbManager.deletePassword(id)
            passwordDbManager.closeDb()
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

    fun savePass(){
        passwordDbManager.openDb()
        val ResourceEditText: EditText = findViewById(R.id.resource_editText)
        val LoginEditText: EditText = findViewById(R.id.login_editText)
        val PassEditText: EditText = findViewById(R.id.password_editText)
        val NoteEditText: EditText = findViewById(R.id.note_editText)
        val id = intent.getIntExtra("id", -1)
        passwordDbManager.updatePassword(id,ResourceEditText.text.toString(),LoginEditText.text.toString(),
            PassEditText.text.toString(),NoteEditText.text.toString())
        passwordDbManager.closeDb()
    }

    fun setText(){
        passwordDbManager.openDb()
        val ResourceEditText: EditText = findViewById(R.id.resource_editText)
        val LoginEditText: EditText = findViewById(R.id.login_editText)
        val PassEditText: EditText = findViewById(R.id.password_editText)
        val NoteEditText: EditText = findViewById(R.id.note_editText)
        val id = intent.getIntExtra("id", -1)
        val dataArray: Array<String>? = passwordDbManager.getInfoFromId(id)
        if (dataArray != null) {
            ResourceEditText.setText(dataArray[0])
            LoginEditText.setText(dataArray[1])
            PassEditText.setText(dataArray[2])
            NoteEditText.setText(dataArray[3])
        }
        passwordDbManager.closeDb()
    }
}