package com.example.noci.notes.input

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.noci.database.Note
import com.example.noci.database.NoteDatabase
import com.example.noci.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InputViewModel(application: Application): AndroidViewModel(application)  {

    private val _insertInitializer = MutableLiveData<Boolean>()
    val insertInitializer : LiveData<Boolean>
        get() = _insertInitializer

    private val readAll: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)
        readAll = repository.readAllData
    }

    fun insertNoteInitializer() {
        _insertInitializer.value = true
    }

    fun insertNote(title: String, description: String) {
        val input = Note(0, title, description)
        insert(input)
        Log.e("this", "Added $input")
    }

    fun showAllNotes() {
        //tonight.value = getTonightFromDatabase()
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }
}