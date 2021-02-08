package com.example.noci.database

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAll()

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

//    if you want to update noteDate
//    suspend fun updateNote(id: Int, newTitle: String, newDate: String, newType: Int) {
//        noteDao.updateNote(id, newTitle, newDate, newType)
//    }

    suspend fun updateNote(id: Int, newTitle: String, newType: Int) {
        noteDao.updateNote(id, newTitle, newType)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

}