package com.example.noci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.example.noci.database.Note
import com.example.noci.notes.MODE_ENABLER
import com.example.noci.notes.input.InputFragmentArgs
import com.orhanobut.hawk.Hawk

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if(ThemeKey.getThemeKey() == "dark_mode") {
            ThemeKey.setThemeKey("dark_mode")
            setTheme(R.style.AppThemeDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if(ThemeKey.getThemeKey() == "light_mode") {
            ThemeKey.setThemeKey("light_mode")
            setTheme(R.style.AppThemeLight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        // get the parcelable object into a variable
        val noteObject = intent?.getParcelableExtra<Note>("note")

        // find the navigation controller, set the new navigation graph and send the object to the new fragments
        if (noteObject != null) {
            findNavController(R.id.input_nav_host_fragment).setGraph(R.navigation.input_navigation, InputFragmentArgs(noteObject).toBundle())
        }

    }

    override fun onPause() {
        super.onPause()

        finish()
    }

}