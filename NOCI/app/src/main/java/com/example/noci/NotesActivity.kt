package com.example.noci

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
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

    lateinit var bottomNavigationView: BottomNavigationView
    private var threadChecker = false

    private var currentNightMode by Delegates.notNull<Int>()

    lateinit var dayHeader: TextView
    lateinit var day_n_night: ImageView

    var threadNameCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {

//        val fadeOut = AlphaAnimation(1f, 0f)
//        fadeOut.interpolator = AccelerateInterpolator() //and this
//        fadeOut.startOffset = 1000
//        fadeOut.duration = 1000
//
//        val animation1 = AnimationSet(false) //change to false
////        animation.addAnimation(fadeIn)
//        animation1.addAnimation(fadeOut)
//
//        val rootView1 = window.decorView.rootView
//
//        rootView1.startAnimation(animation1)

        if (ThemeKey.getThemeKey() == "dark_mode") {
            ThemeKey.setThemeKey("dark_mode")
            setTheme(R.style.AppThemeDark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (ThemeKey.getThemeKey() == "light_mode") {
            ThemeKey.setThemeKey("light_mode")
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
                //notesViewModel.dayNightResetter()
            } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                ThemeKey.setThemeKey("light_mode")
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
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
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

//                    val currentDate = SimpleDateFormat("hh:mm:ss")
//                    val formattedDate = currentDate.format(Date())
//                    Log.e("DATE ", formattedDate.toString())

                    if (dayHeader.text != formattedDate) {
                        dayHeader.text = formattedDate
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

//        val rootView1 = window.decorView.rootView
//
////        val fadeIn: Animation = AlphaAnimation(0f, 1f)
////        fadeIn.duration = 1000
//        val fadeOut: Animation = AlphaAnimation(1f, 0f)
//        fadeOut.startOffset = 1000
//        fadeOut.duration = 1000
//        val animation = AnimationSet(true)
////        animation.addAnimation(fadeIn)
//        animation.addAnimation(fadeOut)
//        rootView1.startAnimation(animation)

//        val rootView = window.decorView.rootView
//
//        rootView.animate()
//            .scaleY(1F) //just wanted to show you possible methods you can add more
//            .setStartDelay(100)
//            .setDuration(2000)
//            .setInterpolator(DecelerateInterpolator())
//            .setListener(object : Animator.AnimatorListener {
//                override fun onAnimationStart(animation: Animator) {
//                    val fadeOut = AlphaAnimation(1f, 0f)
//                    fadeOut.interpolator = AccelerateInterpolator() //and this
//                    fadeOut.startOffset = 1000
//                    fadeOut.duration = 1000
//
//                    val animation1 = AnimationSet(false) //change to false
////        animation.addAnimation(fadeIn)
//                    animation1.addAnimation(fadeOut)
//
//                    val rootView1 = window.decorView.rootView
//
//                    rootView1.startAnimation(animation1)
//
//                    Toast.makeText(applicationContext, "OL", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onAnimationEnd(animation: Animator) {
//                    val fadeOut = AlphaAnimation(1f, 0f)
//                    fadeOut.interpolator = AccelerateInterpolator() //and this
//                    fadeOut.startOffset = 1000
//                    fadeOut.duration = 1000
//
//                    val animation1 = AnimationSet(false) //change to false
////        animation.addAnimation(fadeIn)
//                    animation1.addAnimation(fadeOut)
//
//                    val rootView1 = window.decorView.rootView
//
//                    rootView1.startAnimation(animation1)
//
//                    Toast.makeText(applicationContext, "OL", Toast.LENGTH_SHORT).show()
//                }
//                override fun onAnimationCancel(animation: Animator) {}
//                override fun onAnimationRepeat(animation: Animator) {}
//            }).start()


        ///val animFadeIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)


//        val fadeOut = AlphaAnimation(1f, 0f)
//        fadeOut.interpolator = AccelerateInterpolator() //and this
//        fadeOut.startOffset = 1000
//        fadeOut.duration = 1000
//
//        val animation = AnimationSet(false) //change to false
////        animation.addAnimation(fadeIn)
//        animation.addAnimation(fadeOut)
//
//        //val rootView =
//            (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
//
//        //val rootView = window.decorView.rootView
//
//        val rootView = window.decorView.rootView
//
//        rootView.startAnimation(animation)


//        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.putExtra("NotificationText", "some text")
//        val pendingIntent =
//            PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
//        alarmManager[AlarmManager.RTC_WAKEUP, 1000] = pendingIntent
    }
}