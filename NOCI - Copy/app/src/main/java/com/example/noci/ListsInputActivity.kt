package com.example.noci

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.example.noci.database_lists.ItemList
import com.example.noci.lists.input.ListsInputFragmentArgs
import com.example.noci.notes.MODE_ENABLER
import com.orhanobut.hawk.Hawk

class ListsInputActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        if(ThemeKey.theme == "dark_mode") {
            setThemeKey("dark_mode")
            setTheme(R.style.AppThemeDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if(ThemeKey.theme == "light_mode") {
            setThemeKey("light_mode")
            setTheme(R.style.AppThemeLight)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_lists)

        // get the parcelable object into a variable
        val noteObject = intent?.getParcelableExtra<ItemList>("list")

        // find the navigation controller, set the new navigation graph and send the object to the new fragments
        if (noteObject != null) {
            findNavController(R.id.input_lists_nav_host_fragment).setGraph(R.navigation.lists_navigation, ListsInputFragmentArgs(noteObject).toBundle())
        }

    }

}