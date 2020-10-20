package com.nazarrybickij.cocktailstrike.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Drink(
    @PrimaryKey
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
)