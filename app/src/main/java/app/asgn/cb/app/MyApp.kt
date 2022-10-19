package app.asgn.cb.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        currentApplication = this
    }

    companion object{
        private var currentApplication: MyApp? = null
        fun getInstance(): MyApp? {
            return currentApplication
        }
    }
}