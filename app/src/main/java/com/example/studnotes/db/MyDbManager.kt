package com.example.studnotes.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.ContactsContract
import com.example.studnotes.note.NoteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    fun removeFromDb(id: String){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(UserNotes.TABLE_NAME, selection , null)
    }

    suspend fun readData(searchText: String) : ArrayList<NoteData> = withContext(Dispatchers.IO){
        val dataList = ArrayList<NoteData>()
        val selection = "${UserNotes.COLUMN_NAME_SUBJECT} like ?"
        val cursor = db?.query(UserNotes.TABLE_NAME, null, selection, arrayOf("%$searchText%"), null, null, null)
        while(cursor?.moveToNext()!!){
            val noteData : NoteData = NoteData()
            noteData.subject = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_SUBJECT))
            noteData.title = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_TITLE))
            noteData.note = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_NOTE))
            noteData.imgUri = cursor.getString(cursor.getColumnIndex(UserNotes.COLUMN_NAME_IMAGE_URI))
            noteData.id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))

            dataList.add(noteData)
        }
        cursor.close()
        return@withContext dataList
    }

    fun updateItem(id: Int, subject: String, title: String, note: String, imgUri: String){
        val values = ContentValues().apply {
            put(UserNotes.COLUMN_NAME_SUBJECT, subject)
            put(UserNotes.COLUMN_NAME_TITLE, title)
            put(UserNotes.COLUMN_NAME_NOTE, note)
            put(UserNotes.COLUMN_NAME_IMAGE_URI, imgUri)
        }
        val selection = BaseColumns._ID + "=$id"

        db?.update(UserNotes.TABLE_NAME, values, selection, null)
    }

}