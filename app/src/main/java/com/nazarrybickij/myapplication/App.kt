package com.nazarrybickij.myapplication
import android.app.Application
import android.content.res.Resources

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        getResources = resources
        context = this
    }

    companion object {
        var context:App? = null
        var getResources: Resources? = null
    }
}