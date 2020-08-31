package com.example.noci.database

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAll()

    suspend fun addNote(note: Note) {
        noteDao.insert(note)
    }
}