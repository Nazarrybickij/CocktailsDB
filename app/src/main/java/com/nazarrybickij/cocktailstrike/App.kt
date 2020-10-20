package com.nazarrybickij.cocktailstrike
import android.app.Application
import android.content.res.Resources
import androidx.room.Room
import com.nazarrybickij.cocktailstrike.db.AppRoomDB


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        getResources = resources
        context = this
        database = Room.databaseBuilder<AppRoomDB>(this, AppRoomDB::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        lateinit var database:AppRoomDB
        lateinit var context:App
        lateinit var getResources:Resources
    }
}