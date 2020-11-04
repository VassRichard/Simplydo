package com.example.noci

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.hawk.Hawk

class ListsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        Hawk.init(this).build()

    }
}