package com.example.noci.database_lists


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShopListDao {

    @Insert
    suspend fun addNote(note: ShopLists)

//    @Query("INSERT * INTO note_table WHERE noteId = :key")
//    suspend fun insertType()

    @Query("SELECT * FROM list_table ORDER BY id ASC")
    fun readAll(): LiveData<List<ShopLists>>

    //@Query("SELECT COUNT(*) FROM note_table")
    //fun getTodayTasksCount(): LiveData<String>

    @Query("UPDATE list_table SET noteTitle = :newTitle, noteDate = :newDate WHERE id = :id")
    suspend fun updateNote(id: Int, newTitle: String, newDate: String)

    @Delete
    suspend fun deleteList(noteId: ShopLists)


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