package com.tamizna.yukbisayuk

import android.app.Application

class CustomApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()

        application = this
    }
}