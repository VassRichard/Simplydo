package com.example.noci

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class NotesActivity : AppCompatActivity() {

    lateinit var bottomNavigationView : BottomNavigationView
    private var threadChecker = false

    private var currentNightMode by Delegates.notNull<Int>()

    lateinit var dayHeader: TextView
    lateinit var day_n_night: ImageView

    var threadNameCounter = 0

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
        setContentView(R.layout.activity_notes)

        dayHeader = findViewById(R.id.day_header)
        day_n_night = findViewById(R.id.day_night)

//        if (!threadChecker) {
//            thread.start()
//        }

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

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO){
            applyDayNight(AppCompatDelegate.MODE_NIGHT_NO)
        } else{
            applyDayNight(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onStart() {
        super.onStart()

        day_n_night.setOnClickListener {
                if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                    setThemeKey("dark_mode")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    //notesViewModel.dayNightResetter()
                } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    setThemeKey("light_mode")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    //notesViewModel.dayNightResetter()
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    private fun applyDayNight(state: Int){
        if (state == AppCompatDelegate.MODE_NIGHT_NO){
            //apply day colors for your views
            setTheme(R.style.AppThemeLight)
        }else{
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
                    //val c: Calendar = Calendar.getInstance()

//                    val df = SimpleDateFormat("EEEE", Locale.ENGLISH)
//                    val formattedDate: String = df.format(c.time)

                    val currentDate = SimpleDateFormat("hh:mm:ss")
                    val formattedDate = currentDate.format(Date())


                    Log.e("DATE ", formattedDate.toString())

                    if (dayHeader.text != formattedDate) {
                        dayHeader.text = formattedDate.toString()
                    }

                    sleep(1000)
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun onResume() {
        super.onResume()

        thread = Thread(thread, "Thread #" + String.valueOf(threadNameCounter))
        thread.start()
        Log.e("THREAD IS ", thread.toString())

//        if (!threadChecker) {
//            thread.start()
//            threadChecker = true
//        }
    }

    override fun onPause() {
        super.onPause()

        threadNameCounter++
        thread.interrupt()

        //thread.interrupt()
//        t?.interrupt()
//        threadChecker = false
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