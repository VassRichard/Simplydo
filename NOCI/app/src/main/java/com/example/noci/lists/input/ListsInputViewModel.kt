package com.example.noci.lists.input

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
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

class ListsInputViewModel(application: Application): AndroidViewModel(application)  {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _insertInitializer = MutableLiveData<Boolean>()
    val insertInitializer : LiveData<Boolean>
        get() = _insertInitializer

    private val _insertDateInitializer = MutableLiveData<Boolean>()
    val insertDateInitializer : LiveData<Boolean>
        get() = _insertDateInitializer

    private val _onChangeTitle = MutableLiveData<Boolean>()
    val onChangeTitle : LiveData<Boolean>
        get() = _onChangeTitle

    private val _onGoBackToMain = MutableLiveData<Boolean>()
    val onGoBackToMain : LiveData<Boolean>
        get() = _onGoBackToMain

    private val _addToListBool = MutableLiveData<Boolean>()
    val addToListBool : LiveData<Boolean>
        get() = _addToListBool

    //private val readAll: LiveData<List<Note>>
    private val listRepository: ItemListRepository

    val itemsReadAll: LiveData<List<Items>>
    private val itemsRepository: ItemsRepository


    private var noteType : Int = -1
    private var newNoteType: Int = -1

    init {
        val noteDao = ItemListDatabase.getInstance(application).noteDao
        listRepository = ItemListRepository(noteDao)
        //readAll = repository.readAllData

        val shopNoteDao = ItemsDatabase.getInstance(application).shopNoteDao
        itemsRepository = ItemsRepository(shopNoteDao)

        // change readSpecificData to readAllData if you want to show all items
        itemsReadAll = itemsRepository.readSpecificData!!
    }

    fun insertNoteInitializer() {
        _insertInitializer.value = true
    }

    fun insertNoteDateInitializer() {
        _insertDateInitializer.value = true
    }

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

    fun onChangeTitleObserver() {
        _onChangeTitle.value = true
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

//    fun addToNote(subnote: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            //repository.updateNote(subnote)
//        }
//    }

//    fun onSetNoteType(type: Int) {
//        noteType = type
//        newNoteType = type
//    }
//
//    fun onGoBack() {
//        _onGoBackToMain.value = true
//    }

    fun addToList() {
        _addToListBool.value = true
    }
}