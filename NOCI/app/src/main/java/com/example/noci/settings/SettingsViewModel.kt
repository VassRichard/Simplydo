package com.example.noci.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

<<<<<<< HEAD
const val SWITCH_CHECKED : String = ""

=======
>>>>>>> parent of 86dc984... New app name/splash/logo
class SettingsViewModel : ViewModel() {

    private val _onClickedSwitch = MutableLiveData<Boolean>()
    val onClickedSwitch: LiveData<Boolean>
        get() = _onClickedSwitch


    fun onClickDarkMode() {
        _onClickedSwitch.value = true
    }

}