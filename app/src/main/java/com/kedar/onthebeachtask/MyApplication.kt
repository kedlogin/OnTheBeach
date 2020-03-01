package com.kedar.onthebeachtask

import android.app.Application
import com.kedar.onthebeachtask.di.AppComponent
import com.kedar.onthebeachtask.di.DaggerAppComponent

class MyApplication : Application() {
    val appComponent:AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}