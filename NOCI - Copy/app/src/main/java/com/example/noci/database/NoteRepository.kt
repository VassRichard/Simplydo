package com.example.noci.database

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAll()

    //private val today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

    //val todayTasksCount: LiveData<String> = noteDao.getTodayTasksCount()

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

//    fun getTodayTasksCount(today: String) {
//        noteDao.getTodayTasksCount(today)
//    }

    suspend fun updateNote(id: Int, newTitle: String, newDate: String, newType: Int) {
        noteDao.updateNote(id, newTitle, newDate, newType)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

}