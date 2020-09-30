package com.example.noci.splashscreen

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.noci.MainActivity
import com.example.noci.R
import com.example.noci.settings.SWITCH_CHECKED
import com.orhanobut.hawk.Hawk

class Splashscreen : AppCompatActivity() {

    private val handler = Handler()

    private val runnable = Runnable {

        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}