package com.example.noci.splashscreen

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatDelegate
>>>>>>> parent of 86dc984... New app name/splash/logo
import com.example.noci.MainActivity
import com.example.noci.R
import com.example.noci.settings.SWITCH_CHECKED
import com.orhanobut.hawk.Hawk

class Splashscreen : AppCompatActivity() {

    private val handler = Handler()

    private val runnable = Runnable {

<<<<<<< HEAD
        val theme = Hawk.get<String>(SWITCH_CHECKED)
        if (theme != null) {
            application.setTheme(R.style.AppThemeDark)
        } else {
            application.setTheme(R.style.AppTheme)
        }
=======
>>>>>>> parent of 86dc984... New app name/splash/logo
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