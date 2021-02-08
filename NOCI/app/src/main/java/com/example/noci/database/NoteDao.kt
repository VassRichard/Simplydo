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

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun readAll(): LiveData<List<Note>>

//    if you want to update noteDate
//    @Query("UPDATE note_table SET noteTitle = :newTitle, noteDate = :newDate, noteType = :newType WHERE id = :id")
//    suspend fun updateNote(id: Int, newTitle: String, newDate: String, newType: Int)

    @Query("UPDATE note_table SET noteTitle = :newTitle, noteType = :newType WHERE id = :id")
    suspend fun updateNote(id: Int, newTitle: String, newType: Int)

    @Delete
    suspend fun deleteNote(noteId: Note)

}