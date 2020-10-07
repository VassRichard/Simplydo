package com.example.noci.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _onClickedSwitch = MutableLiveData<Boolean>()
    val onClickedSwitch: LiveData<Boolean>
        get() = _onClickedSwitch

    private val _onGoBackToMain = MutableLiveData<Boolean>()
    val onGoBackToMain : LiveData<Boolean>
        get() = _onGoBackToMain

    fun onClickDarkMode() {
        _onClickedSwitch.value = true
    }

    fun onGoBack() {
        _onGoBackToMain.value = true
    }

}