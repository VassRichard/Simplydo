package com.example.noci.lists.input

import android.app.Application
import android.app.PendingIntent.FLAG_NO_CREATE
import android.app.PendingIntent.getActivity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import com.example.noci.ListsInputActivity
import com.example.noci.database.Note
import com.example.noci.database_lists.ItemListDatabase
import com.example.noci.database_lists.ItemListRepository
import com.example.noci.database_lists.items.Items
import com.example.noci.database_lists.items.ItemsDatabase
import com.example.noci.database_lists.items.ItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListsInputViewModel(application: Application) : AndroidViewModel(application) {

    /// ------------------------------- CONTEXT ------------------------------- ///

    private val context = getApplication<Application>().applicationContext

    /// ------------------------------- COROUTINES (BACKGROUND THREAD JOBS) INITALIZERS ------------------------------- ///

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /// ------------------------------- MUTABLELIVEDATA/LIVEDATA VARIABLES ------------------------------- ///

    private val _insertInitializer = MutableLiveData<Boolean>()
    val insertInitializer: LiveData<Boolean>
        get() = _insertInitializer

    private val _insertDateInitializer = MutableLiveData<Boolean>()
    val insertDateInitializer: LiveData<Boolean>
        get() = _insertDateInitializer

    private val _onChangeTitle = MutableLiveData<Boolean>()
    val onChangeTitle: LiveData<Boolean>
        get() = _onChangeTitle

    private val _onGoBackToMain = MutableLiveData<Boolean>()
    val onGoBackToMain: LiveData<Boolean>
        get() = _onGoBackToMain

    private val _addToListBool = MutableLiveData<Boolean>()
    val addToListBool: LiveData<Boolean>
        get() = _addToListBool

    private val _onSelectAllBool = MutableLiveData<Boolean>()
    val onSelectAllBool: LiveData<Boolean>
        get() = _onSelectAllBool

    private val _onDeleteSelectedBool = MutableLiveData<Boolean>()
    val onDeleteSelectedBool: LiveData<Boolean>
        get() = _onDeleteSelectedBool

    private val _onCopyDataBool = MutableLiveData<Boolean>()
    val onCopyDataBool: LiveData<Boolean>
        get() = _onCopyDataBool

    /// ------------------------------- DATABASE REPOSITORY INITIALIZERS ------------------------------- ///

    //private val readAll: LiveData<List<Note>>
    private val listRepository: ItemListRepository

    val itemsReadAll: LiveData<List<Items>>
    private val itemsRepository: ItemsRepository

    var dataStorageList: List<String>? = null

    /// ------------------------------- INITIALIZER ------------------------------- ///

    init {
        val noteDao = ItemListDatabase.getInstance(application).noteDao
        listRepository = ItemListRepository(noteDao)
        //readAll = repository.readAllData

        val shopNoteDao = ItemsDatabase.getInstance(application).shopNoteDao
        itemsRepository = ItemsRepository(shopNoteDao)

        // change readSpecificData to readAllData if you want to show all items
        itemsReadAll = itemsRepository.readSpecificData!!
    }

    /// ------------------------------- DATABASE FUNCTIONS ------------------------------- ///

    fun addNote(title: String, noteId: Int) {
        val input = Items(0, title, noteId, false)
        insert(input)
        Log.e("this", "Added $input")
    }

    fun insert(note: Items) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.addNote(note)
        }
    }

    fun updateTitle(titleId: Int, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.updateTitle(titleId, title)
        }
    }

    fun selectAllItems(listId: Int, stateToBeSet: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.selectAll(listId, stateToBeSet)
        }
    }

    fun copyDataToClip(listId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStorageList = itemsRepository.copyData(listId)
        }
    }

    fun changeItemState(id: Int, newState: Boolean) {
        uiScope.launch {
            itemsRepository.editNote(id, newState)
        }
    }

    fun deleteFromLocalDB(item: Items) {
        uiScope.launch {
            itemsRepository.deleteNote(item)
        }
    }

    fun onDeleteSelectedItems(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsRepository.onDeleteSelected(itemId)
        }
    }

    /// ------------------------------- MUTABLELIVEDATA/LIVEDATA FUNCTIONS ------------------------------- ///

    fun addToList() {
        _addToListBool.value = true
    }

    fun onSelectAll() {
        _onSelectAllBool.value = true
    }

    fun onCopyData() {
        _onCopyDataBool.value = true
    }

    fun onChangeTitleObserver() {
        _onChangeTitle.value = true
    }

    fun onDeleteSelected() {
        _onDeleteSelectedBool.value = true
    }
}