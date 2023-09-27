package com.example.taking_notes_app_vesrion_2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context):SQLiteOpenHelper(context , DATABASE_NAME ,null , DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCONTENT = "descontent"

    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY ,$COLUMN_TITLE TEXT, $COLUMN_DESCONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note:NoteDataClass){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_DESCONTENT,note.descontent)
        }
        db.insert(TABLE_NAME,null, values)
        db.close()
    }

    fun getAllNotes():List<NoteDataClass>{
        val listnotes = mutableListOf<NoteDataClass>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query , null)


        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val descontent = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCONTENT))

            val note = NoteDataClass(id,title,descontent)
            listnotes.add(note)
        }
        cursor.close()
        db.close()
        return listnotes

    }

    fun NoteUpdate(note:NoteDataClass){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_DESCONTENT,note.descontent)
        }
        val Where = "$COLUMN_ID = ?"
        val Arguments = arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,Where,Arguments)
        db.close()
    }

    fun getNotesByID(noteId:Int):NoteDataClass{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val descontent = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCONTENT))


        cursor.close()
        db.close()
        return NoteDataClass(id, title, descontent)

    }

    fun NoteDelete(noteId: Int){
        val db = writableDatabase
        val WhereDel= "$COLUMN_ID = ?"
        val ArgumentsDel = arrayOf(noteId.toString())
        db.delete(TABLE_NAME,WhereDel,ArgumentsDel)
        db.close()
    }
}