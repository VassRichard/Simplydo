package com.example.noci

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.example.noci.database_lists.ItemList
import com.example.noci.lists.input.ListsInputFragmentArgs
import com.orhanobut.hawk.Hawk

class ListsInputActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val mode = Hawk.get("KEY_MODE", "light_mode")

        if (mode == "light_mode") {
            Hawk.put("KEY_MODE", "light_mode")
            applyDayNight(AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else if (mode == "dark_mode") {
            Hawk.put("KEY_MODE", "dark_mode")
            applyDayNight(AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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

    private fun applyDayNight(state: Int) {
        if (state == AppCompatDelegate.MODE_NIGHT_NO) {
            //apply day colors for your views
            setTheme(R.style.AppThemeLight)
        } else {
            //apply night colors for your views
            setTheme(R.style.AppThemeDark)
        }
    }

    override fun onPause() {
        super.onPause()

        finish()
    }

}