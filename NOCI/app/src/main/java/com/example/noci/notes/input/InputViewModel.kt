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

    private val _insertDateInitializer = MutableLiveData<Boolean>()
    val insertDateInitializer : LiveData<Boolean>
        get() = _insertDateInitializer

    private val _onGoBackToMain = MutableLiveData<Boolean>()
    val onGoBackToMain : LiveData<Boolean>
        get() = _onGoBackToMain

    private val readAll: LiveData<List<Note>>
    private val repository: NoteRepository

    private var noteType : Int = -1
    private var newNoteType: Int = -1

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)
        readAll = repository.readAllData
    }

    fun insertNoteInitializer() {
        _insertInitializer.value = true
    }

    fun insertNoteDateInitializer() {
        _insertDateInitializer.value = true
    }

    fun insertNote(title: String, date: String) {
        val input = Note(0, title, date, type = noteType)
        insert(input)
        Log.e("this", "Added $input")
    }

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(id: Int, newTitle: String, newDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(id, newTitle, newDate, newNoteType)
        }
    }

    fun onSetNoteType(type: Int) {
        noteType = type
        newNoteType = type
    }

    fun onGoBack() {
        _onGoBackToMain.value = true
    }
}