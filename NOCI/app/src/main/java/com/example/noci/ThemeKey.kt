package com.example.noci

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString


object ThemeKey {

    private var theme: String = "light_mode"

    init {

    }

    fun setThemeKey(currentTheme: String) {
        this.theme = currentTheme
    }

    fun getThemeKey() : String {
        return theme
    }

}

object SelectKey {
    private var select: Boolean = false

    init {

    }

    fun setSelect(select: Boolean) {
        this.select = select
    }

    fun getSelect(): Boolean {
        return select
    }

}

object typeKey {
    private var mark: Boolean = false

    init {

    }

    fun setMark(mark: Boolean) {
        this.mark = mark
    }

    fun getMark(): Boolean {
        return mark
    }
}