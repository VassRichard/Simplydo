package com.example.noci.database_lists


import androidx.lifecycle.LiveData

class ItemListRepository(private val noteDao: ItemListDao) {

    val readAllData: LiveData<List<ItemList>> = noteDao.readAll()

    suspend fun addNote(note: ItemList) {
        noteDao.addNote(note)
    }

//    suspend fun updateNote(id: Int, newTitle: String, newDate: String) {
//        noteDao.updateNote(id, newTitle, newDate)
//    }

    suspend fun updateTitle(titleId: Int, title: String) {
        noteDao.updateTitle(titleId, title)
    }

    suspend fun deleteList(note: ItemList) {
        noteDao.deleteList(note)
    }

}