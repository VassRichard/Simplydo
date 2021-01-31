package com.example.noci.database_lists


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemListDao {

    @Insert
    suspend fun addNote(note: ItemList)

    @Query("SELECT * FROM list_table ORDER BY id ASC")
    fun readAll(): LiveData<List<ItemList>>

    @Query("UPDATE list_table SET noteTitle = :newTitle, noteDate = :newDate WHERE id = :id")
    suspend fun updateNote(id: Int, newTitle: String, newDate: String)

    @Query("UPDATE list_table SET noteTitle = :title WHERE id = :titleId")
    suspend fun updateTitle(titleId: Int, title: String)

    @Delete
    suspend fun deleteList(noteId: ItemList)

}