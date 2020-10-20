package com.nazarrybickij.cocktailstrike.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nazarrybickij.cocktailstrike.entity.Drink


@Dao
interface DrinkDao {
    @Insert
    fun insertAll(drink: Drink)

    @Delete
    fun delete(drink: Drink)

    @Query("SELECT * FROM drink")
    fun getAll(): List<Drink>

}