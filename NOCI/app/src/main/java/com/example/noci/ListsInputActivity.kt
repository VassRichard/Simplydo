package com.example.noci

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.noci.database_lists.ShopLists
import com.example.noci.lists.input.ListsInputFragmentArgs
import com.orhanobut.hawk.Hawk

class ListsInputActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_lists)

        Hawk.init(this).build()

        // get the parcelable object into a variable
        val noteObject = intent?.getParcelableExtra<ShopLists>("list")

        // find the navigation controller, set the new navigation graph and send the object to the new fragments
        if (noteObject != null) {
            findNavController(R.id.input_lists_nav_host_fragment).setGraph(R.navigation.lists_navigation, ListsInputFragmentArgs(noteObject).toBundle())
        }

    }

}