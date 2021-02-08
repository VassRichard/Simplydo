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

const val PLM: String = ""
const val ITEM_DELETER_CHECKER : String = ""

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _onAddBoolean = MutableLiveData<Boolean>()
    val onAddBoolean: LiveData<Boolean>
        get() = _onAddBoolean

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

        val noteDao = ItemListDatabase.getInstance(application).noteDao
        repository = ItemListRepository(noteDao)

        readAllData = repository.readAllData

        val itemDao = ItemsDatabase.getInstance(application).shopNoteDao
        itemRepository = ItemsRepository(itemDao)
    }

    fun onAdd() {
        _onAddBoolean.value = true
    }

    fun onAddResetter() {
        _onAddBoolean.value = false
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}