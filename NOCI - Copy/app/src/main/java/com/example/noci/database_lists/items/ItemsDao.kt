package com.example.noci.database_lists.items

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemsDao {

    @Insert
    suspend fun addNote(note: Items)

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun readAll(): LiveData<List<Items>>

    @Query("SELECT * FROM item_table WHERE listId = :listId ORDER BY id ASC")
    fun readSpecificData(listId: Int): LiveData<List<Items>>

    @Query("SELECT nameTitle FROM item_table WHERE listId = :listId ORDER BY id ASC")
    suspend fun copyData(listId: Int): List<String>?

    @Query("UPDATE item_table SET itemState = :stateToBeSet WHERE listId = :listId")
    suspend fun selectAll(listId: Int, stateToBeSet: Boolean)

    @Query("UPDATE item_table SET itemState = :newState WHERE id = :listId")
    suspend fun editItem(listId: Int, newState: Boolean)

    @Delete
    suspend fun deleteNote(noteId: Items)

    @Query( "DELETE FROM item_table WHERE listId = :id")
    suspend fun deleteItemsFromSpecificList(id: Int)

    @Query("DELETE FROM item_table WHERE listId = :id AND itemState == 1")
    suspend fun onDeleteSelected(id: Int)

}