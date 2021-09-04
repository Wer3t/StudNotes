package com.example.studnotes.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract
import com.example.studnotes.note.NoteData

class MyDbManager (val context: Context){
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db = myDbHelper.writableDatabase
    }

    fun closeDb(){
        db?.close()
    }

    fun insertToDb(subject: String, title: String, note: String, imgUri: String){
        val values = ContentValues().apply {
            put(UserNotes.COLUMN_NAME_SUBJECT, subject)
            put(UserNotes.COLUMN_NAME_TITLE, title)
            put(UserNotes.COLUMN_NAME_NOTE, note)
            put(UserNotes.COLUMN_NAME_IMAGE_URI, imgUri)
        }

        db?.insert(UserNotes.TABLE_NAME, null, values)
    }

    fun readData() : ArrayList<NoteData>{
        val dataList = ArrayList<NoteData>()
        val cursor = db?.query(UserNotes.TABLE_NAME, null, null, null, null, null, null)
        while(cursor?.moveToNext()!!){
            val noteData : NoteData = NoteData()
            noteData.subject = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_SUBJECT))
            noteData.title = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_TITLE))
            noteData.note = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_NOTE))
            noteData.imgUri = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_IMAGE_URI))

            dataList.add(noteData)
        }
        cursor.close()
        return dataList
    }
}