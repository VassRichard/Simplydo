package com.example.noci.notes.input

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.noci.database.Note
import com.example.noci.database.NoteDatabase
import com.example.noci.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputViewModel(application: Application): AndroidViewModel(application)  {

    private val _insertInitializer = MutableLiveData<Boolean>()
    val insertInitializer : LiveData<Boolean>
        get() = _insertInitializer

    private val readAll: LiveData<List<Note>>
    private val repository: NoteRepository

    private var noteType : Int = -1

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)
        readAll = repository.readAllData
    }

    fun insertNoteInitializer() {
        _insertInitializer.value = true
    }

    fun insertNote(title: String, description: String) {
        val input = Note(0, title, description, type = noteType)
        insert(input)
        Log.e("this", "Added $input")
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun onSetNoteType(quality: Int) {
        noteType = quality
    }
}