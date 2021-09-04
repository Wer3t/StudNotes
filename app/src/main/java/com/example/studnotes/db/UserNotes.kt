package com.example.studnotes.db

import android.provider.BaseColumns

object UserNotes: BaseColumns {
    const val TABLE_NAME = "user_notes"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_NOTE = "note"
    const val COLUMN_NAME_SUBJECT = "subject"
    const val COLUMN_NAME_IMAGE_URI = "img_uri"


    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT, $COLUMN_NAME_NOTE TEXT, $COLUMN_NAME_SUBJECT  TEXT, $COLUMN_NAME_IMAGE_URI TEXT)"
    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}