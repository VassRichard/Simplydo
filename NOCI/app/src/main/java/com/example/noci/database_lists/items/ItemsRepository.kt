package com.example.noci.database_lists.items

import androidx.lifecycle.LiveData
import com.example.listprototype.list.CURRENT_LIST
import com.orhanobut.hawk.Hawk

class ItemsRepository(private val itemsNoteDao: ItemsDao) {

    val readAllData: LiveData<List<Items>> = itemsNoteDao.readAll()

    val theAlmightyID = Hawk.get<String>(CURRENT_LIST)

    val readSpecificData: LiveData<List<Items>> = itemsNoteDao.readSpecificData(theAlmightyID.toInt())

    //private val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

    //val todayTasksCount: LiveData<String> = noteDao.getTodayTasksCount()

    suspend fun addNote(note: Items) {
        itemsNoteDao.addNote(note)
    }

//    fun getTodayTasksCount(today: String) {
//        noteDao.getTodayTasksCount(today)
//    }

    suspend fun deleteNote(note: Items) {
        itemsNoteDao.deleteNote(note)
    }

}