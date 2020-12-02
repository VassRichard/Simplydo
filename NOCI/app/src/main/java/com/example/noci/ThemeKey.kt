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

//fun theme_change(currentTheme: String): String {
//    if(currentTheme == "dark_mode") {
//        return "light_mode"
//    } else {
//        return "dark_mode"
//    }
//}