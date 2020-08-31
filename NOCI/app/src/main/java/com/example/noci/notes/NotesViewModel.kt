package com.example.noci.notes

import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.*
import com.example.noci.database.Note
import com.example.noci.database.NoteDatabase
import com.example.noci.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val _goToInput = MutableLiveData<Boolean>()
    val goToInput: LiveData<Boolean>
        get() = _goToInput

    val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun goToInputNote() {
        _goToInput.value = true
    }

    fun resetGoToInput() {
        _goToInput.value = false
    }

}