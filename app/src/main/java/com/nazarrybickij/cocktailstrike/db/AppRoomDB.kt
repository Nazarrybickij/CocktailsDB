package com.nazarrybickij.cocktailstrike.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazarrybickij.cocktailstrike.entity.Drink

@Database(entities = [Drink::class], version = 1, exportSchema = false)
 abstract class AppRoomDB : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao

}