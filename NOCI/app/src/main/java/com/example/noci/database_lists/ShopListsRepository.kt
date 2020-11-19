package com.example.noci.database_lists


import androidx.lifecycle.LiveData
import com.example.noci.database_lists.items.Items
import com.example.noci.database_lists.items.ItemsDao

class ShopListsRepository(private val noteDao: ShopListDao) {

    val readAllData: LiveData<List<ShopLists>> = noteDao.readAll()

    //private val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

    //val todayTasksCount: LiveData<String> = noteDao.getTodayTasksCount()

    suspend fun addNote(note: ShopLists) {
        noteDao.addNote(note)
    }

//    fun getTodayTasksCount(today: String) {
//        noteDao.getTodayTasksCount(today)
//    }

    suspend fun updateNote(id: Int, newTitle: String, newDate: String) {
        noteDao.updateNote(id, newTitle, newDate)
    }

    suspend fun deleteList(note: ShopLists) {
        noteDao.deleteList(note)
    }

    suspend fun deleteItemsFromSpecificList(itemsId: Int) {
        //itemDao.deleteItemsFromSpecificList(itemsId)
    }

}