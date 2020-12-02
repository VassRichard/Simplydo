package com.example.noci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.noci.notes.MODE_ENABLER
import com.orhanobut.hawk.Hawk

class ListsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Hawk.init(this).build()

        val theme = Hawk.get<String>(MODE_ENABLER, "light_mode")

        if (theme == "dark_mode") {
            Hawk.put(MODE_ENABLER, "dark_mode")
            setTheme(R.style.AppThemeDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if(theme == "light_mode") {
            Hawk.put(MODE_ENABLER, "light_mode")
            setTheme(R.style.AppThemeLight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

    }
}