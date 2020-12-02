package com.example.noci.utils

import android.app.Application
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Hawk.init(this)
            .build()

        startKoin {
            androidContext(this@MyApplication)
            //modules(listOf(myModule))
        }

        //createNotificationChannel()

    }
}
