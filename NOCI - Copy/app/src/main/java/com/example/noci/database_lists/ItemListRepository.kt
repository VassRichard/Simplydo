package com.example.noci.database_lists


import androidx.lifecycle.LiveData

class ItemListRepository(private val noteDao: ItemListDao) {

    val readAllData: LiveData<List<ItemList>> = noteDao.readAll()

    //private val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

    //val todayTasksCount: LiveData<String> = noteDao.getTodayTasksCount()

    suspend fun addNote(note: ItemList) {
        noteDao.addNote(note)
    }

//    fun getTodayTasksCount(today: String) {
//        noteDao.getTodayTasksCount(today)
//    }

    suspend fun updateNote(id: Int, newTitle: String, newDate: String) {
        noteDao.updateNote(id, newTitle, newDate)
    }

    suspend fun updateTitle(titleId: Int, title: String) {
        noteDao.updateTitle(titleId, title)
    }

    suspend fun deleteList(note: ItemList) {
        noteDao.deleteList(note)
    }

    suspend fun deleteItemsFromSpecificList(itemsId: Int) {
        //itemDao.deleteItemsFromSpecificList(itemsId)
    }

}