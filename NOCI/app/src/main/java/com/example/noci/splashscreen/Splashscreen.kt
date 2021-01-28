package com.example.noci.splashscreen

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.noci.MainActivity
import com.example.noci.NotesActivity

class Splashscreen : AppCompatActivity() {

    private val handler = Handler()

    private val runnable = Runnable {

        startActivity(Intent(applicationContext, NotesActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 100)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}