package com.example.noci.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _onGoToNotes = MutableLiveData<Boolean>()
    val onGoToNotes : LiveData<Boolean>
        get() = _onGoToNotes

    private val _onGoToLists = MutableLiveData<Boolean>()
    val onGoToLists : LiveData<Boolean>
        get() = _onGoToLists

    private val _onDayNightSwitch = MutableLiveData<Boolean>()
    val onDayNightSwitch : LiveData<Boolean>
        get() = _onDayNightSwitch

    init {

    }

    fun dayNightSwitcher() {
        _onDayNightSwitch.value = true
    }

    fun goToNotes() {
        _onGoToNotes.value = true
    }

    fun goToLists() {
        _onGoToLists.value = true
    }
}