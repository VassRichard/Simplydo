package com.example.noci

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.noci.notification.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.orhanobut.hawk.Hawk

const val MODE_ENABLER: String = ""

class NotesActivity : AppCompatActivity() {
    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        Hawk.init(this).build()

        val theme = Hawk.get<String>(MODE_ENABLER)

        if (theme == "dark_mode") {
            setTheme(R.style.AppThemeDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setTheme(R.style.AppTheme)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

    }

    override fun onStart() {
        super.onStart()

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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    override fun onDestroy() {
        super.onDestroy()

//        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.putExtra("NotificationText", "some text")
//        val pendingIntent =
//            PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
//        alarmManager[AlarmManager.RTC_WAKEUP, 1000] = pendingIntent
    }
}