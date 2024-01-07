package com.example.password_manager

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.password_manager.db.PasswordDbClass

class PasswordCursorAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        // Создаем новый элемент списка (View) при необходимости
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        // Получаем данные из курсора и связываем их с элементом списка
        val resourceColumnIndex = cursor.getColumnIndex(PasswordDbClass.COLUMN_RESOURCE)
        val resourceName = cursor.getString(resourceColumnIndex)
        val textViewResource = view.findViewById<TextView>(android.R.id.text1)
        textViewResource.text = resourceName
    }
}
