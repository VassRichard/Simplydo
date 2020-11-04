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


class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    private val _currentTime = MutableLiveData<String>()
//    val currentTime: LiveData<String>
//        get() = _currentTime

    private val _goToInput = MutableLiveData<Boolean>()
    val goToInput: LiveData<Boolean>
        get() = _goToInput

    private val _onClickedSwitch = MutableLiveData<Boolean>()
    val onClickedSwitch: LiveData<Boolean>
        get() = _onClickedSwitch

    private val _switch = MutableLiveData<Boolean>()
    val switch : LiveData<Boolean>
        get() = _switch

    private val repository: NoteRepository

    val readAllData: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getInstance(application).noteDao
        repository = NoteRepository(noteDao)

        readAllData = repository.readAllData

    }

    fun goToInputNote() {
        _goToInput.value = true
    }

    fun dayNightSwitcher() {
        _onClickedSwitch.value = true
    }

    fun dayNightResetter() {
        _onClickedSwitch.value = false
    }


//    fun goToListsFragment() {
//        _goToLists.value = true
//    }

    fun switchTo() {
        _switch.value = true
    }

    fun resetGoToInput() {
        _goToInput.value = false
    }

    fun deleteFromLocalDB(note: Note) {
        uiScope.launch {
            repository.deleteNote(note)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}