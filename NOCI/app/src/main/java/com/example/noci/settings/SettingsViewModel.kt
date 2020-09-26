package com.example.noci.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _onClickedSwitch = MutableLiveData<Boolean>()
    val onClickedSwitch: LiveData<Boolean>
        get() = _onClickedSwitch


    fun onClickDarkMode() {
        _onClickedSwitch.value = true
    }

}