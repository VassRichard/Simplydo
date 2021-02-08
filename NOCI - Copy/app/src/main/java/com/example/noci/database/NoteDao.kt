package com.example.noci.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

//    @Query("INSERT * INTO note_table WHERE noteId = :key")
//    suspend fun insertType()

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun readAll(): LiveData<List<Note>>

    //@Query("SELECT COUNT(*) FROM note_table")
    //fun getTodayTasksCount(): LiveData<String>

    @Query("UPDATE note_table SET noteTitle = :newTitle, noteDate = :newDate, noteType = :newType WHERE id = :id")
    suspend fun updateNote(id: Int, newTitle: String, newDate: String, newType: Int)

    @Delete
    suspend fun deleteNote(noteId: Note)

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