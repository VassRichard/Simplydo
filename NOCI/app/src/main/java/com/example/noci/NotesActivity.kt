package com.example.noci

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class NotesActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    private var threadChecker = false

    private var currentNightMode by Delegates.notNull<Int>()

    lateinit var dayHeader: TextView
    lateinit var day_n_night: ImageView

    var threadNameCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        if (ThemeKey.getThemeKey() == "light_mode") {
            ThemeKey.setThemeKey("light_mode")
            applyDayNight(AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else if (ThemeKey.getThemeKey() == "dark_mode") {
            ThemeKey.setThemeKey("dark_mode")
            applyDayNight(AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        dayHeader = findViewById(R.id.day_header)
        day_n_night = findViewById(R.id.day_night)

        currentNightMode = AppCompatDelegate.getDefaultNightMode()

        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
            day_n_night.setBackgroundResource(R.drawable.mode_night)
        } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            day_n_night.setBackgroundResource(R.drawable.mode_day)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            applyDayNight(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            applyDayNight(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onStart() {
        super.onStart()

        day_n_night.setOnClickListener {
            if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                ThemeKey.setThemeKey("dark_mode")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                ThemeKey.setThemeKey("light_mode")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

//        val notifyIntent = Intent(this, MyReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this.applicationContext,
//            NOTIFICATION_SERVICE,
//            notifyIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        this.applicationContext.getSystemService(Context.ALARM_SERVICE).setRepeating(
//            AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
//            1000 * 60 * 60 * 24.toLong(), pendingIntent
//         )

        // setup bottom navigation view
        setUpNavigation()

    }

    fun setUpNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.notes_nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
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

    private
    var thread: Thread = object : Thread() {
        override fun run() {
            try {
                threadChecker = true
                while (!this.isInterrupted) {
                    val c: Calendar = Calendar.getInstance()

                    val df = SimpleDateFormat("EEEE", Locale.ENGLISH)
                    val formattedDate = df.format(c.time).toString()

                    if (dayHeader.text != formattedDate) {
                        dayHeader.text = formattedDate
                    }

                    sleep(1000)
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        moveTaskToBack(true)
    }

    override fun onResume() {
        super.onResume()

        if (!threadChecker) {
            thread.start()
            threadChecker = true
        }
    }

    override fun onPause() {
        super.onPause()

        threadNameCounter++
        thread.interrupt()
    }

}