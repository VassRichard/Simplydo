package com.example.noci.lists

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.noci.database_lists.ItemList
import com.example.noci.database_lists.ItemListDatabase
import com.example.noci.database_lists.ItemListRepository
import com.example.noci.database_lists.items.ItemsDatabase
import com.example.noci.database_lists.items.ItemsRepository
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val ITEM_DELETER_CHECKER : String = ""

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _booleanChecker = MutableLiveData<Boolean>()
    val booleanChecker: LiveData<Boolean>
        get() = _booleanChecker

    private val _goToInput = MutableLiveData<Boolean>()
    val goToInput: LiveData<Boolean>
        get() = _goToInput

    private val _switch = MutableLiveData<Boolean>()
    val switch : LiveData<Boolean>
        get() = _switch

    private val repository: ItemListRepository
    val readAllData: LiveData<List<ItemList>>

    private val itemRepository: ItemsRepository

    val context = getApplication<Application>().applicationContext

    init {
        //Hawk.init(context).build()

        val noteDao = ItemListDatabase.getInstance(application).noteDao
        repository = ItemListRepository(noteDao)

        readAllData = repository.readAllData

        val itemDao = ItemsDatabase.getInstance(application).shopNoteDao
        itemRepository = ItemsRepository(itemDao)
    }

    fun onGoTo() {
        _booleanChecker.value = true
    }

    fun resetGoToInput() {
        _goToInput.value = false
    }

    fun addNote(title: String, date: String) {
        val input = ItemList(0, title, date)
        insert(input)
        Log.e("this", "Added $input")
    }

    fun insert(note: ItemList) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun deleteListFromDB(note: ItemList) {
        uiScope.launch {
            repository.deleteList(note)

            Hawk.init(context).build()

            val id = note.id
            Hawk.put(ITEM_DELETER_CHECKER, "$id")

            itemRepository.deleteItemsFromSpecificList()
        }
    }

    fun goToInputNote() {
        _goToInput.value = true
    }

    fun switchTo() {
        _switch.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}