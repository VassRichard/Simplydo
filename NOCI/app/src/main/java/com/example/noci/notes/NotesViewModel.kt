package com.example.noci.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noci.database.Note
import com.example.noci.database.NoteDatabase
import com.example.noci.database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val MODE_ENABLER: String = ""

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    /// ------------------------------- COROUTINES (BACKGROUND THREAD JOBS) INITALIZERS ------------------------------- ///

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /// ------------------------------- MUTABLELIVEDATA/LIVEDATA VARIABLES ------------------------------- ///

    private val _goToInput = MutableLiveData<Boolean>()
    val goToInput: LiveData<Boolean>
        get() = _goToInput

    /// ------------------------------- DATABASE REPOSITORY INITIALIZERS ------------------------------- ///

    private val repository: NoteRepository

    /// ------------------------------- STORAGE FOR DATABASE DATA ------------------------------- ///

    val readAllData: LiveData<List<Note>>

    /// ------------------------------- INITIALIZER ------------------------------- ///

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)

        readAllData = repository.readAllData
    }

    /// ------------------------------- DATABASE FUNCTIONS ------------------------------- ///

    fun deleteFromLocalDB(note: Note) {
        uiScope.launch {
            repository.deleteNote(note)
        }
    }

    /// ------------------------------- MUTABLELIVEDATA/LIVEDATA FUNCTIONS ------------------------------- ///

    fun goToInputNote() {
        _goToInput.value = true
    }

    fun goToInputNoteResetter() {
        _goToInput.value = false
    }

    /// ------------------------------- VIEWMODEL LIFECYCLE FUNCTIONS ------------------------------- ///

    override fun onCleared() {
        super.onCleared()

        viewModelJob.cancel()
    }

}