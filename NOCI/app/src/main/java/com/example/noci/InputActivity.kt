package com.example.noci

import com.example.noci.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.noci.settings.SWITCH_CHECKED
import com.orhanobut.hawk.Hawk

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Hawk.init(this).build()

        val theme = Hawk.get<String>(SWITCH_CHECKED)
        if (theme == "dark_mode") {
            setTheme(R.style.AppThemeDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setTheme(R.style.AppTheme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
    }

}