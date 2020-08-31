package com.example.noci.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note : Note)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun readAll() : LiveData<List<Note>>


//    @Update
//    fun update(note: NoteAttr)
//
//    @Query("SELECT * FROM notes_attributes WHERE noteId = :key")
//    fun get(key : Long): NoteAttr
//
//    @Query("DELETE FROM notes_attributes")
//    fun clear()
//
//    @Query("SELECT * FROM notes_attributes ORDER BY noteId DESC")
//    fun getAllNights(): LiveData<List<NoteAttr>>
//
//    @Query("SELECT * FROM notes_attributes ORDER BY noteId DESC LIMIT 1")
//    fun getTonight(): NoteAttr?
}