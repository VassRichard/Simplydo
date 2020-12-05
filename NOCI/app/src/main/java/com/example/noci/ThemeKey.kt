package com.example.noci

import com.example.noci.ThemeKey.theme

object ThemeKey {

    var theme: String = "light_mode"

    init {

    }

}

fun setThemeKey(currentTheme: String) {
    theme = currentTheme
}