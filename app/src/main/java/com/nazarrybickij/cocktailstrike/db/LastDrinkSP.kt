package com.nazarrybickij.cocktailstrike.db

import android.content.Context
import android.content.SharedPreferences
import com.nazarrybickij.cocktailstrike.App
import com.nazarrybickij.cocktailstrike.entity.Drink

class LastDrinkSP(var pref: SharedPreferences) {
    companion object {
        val APP_PREFERENCES = "last_drink"
        val ID_DRINK = "id"
        val NAME_DRINK = "name"
        val IMAGE_DRINK = "image"

        private  var instance: LastDrinkSP? = null
        fun getInstance(): LastDrinkSP{
            if (instance == null) {
                val sharedPreferences = App.context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                instance = LastDrinkSP(sharedPreferences)
            }
            return instance as LastDrinkSP
        }
    }


    fun saveLastDrink(drink: Drink) {
        val editor = pref.edit()
        editor.putString(ID_DRINK, drink.idDrink)
        editor.putString(NAME_DRINK, drink.strDrink)
        editor.putString(IMAGE_DRINK, drink.strDrinkThumb)
        editor.apply()
    }

    fun getLastDrink(): Drink {
        if (pref.contains(ID_DRINK)) {
            val id = pref.getString(ID_DRINK,"").toString()
            val name = pref.getString(NAME_DRINK, "").toString()
            val image = pref.getString(IMAGE_DRINK, "").toString()
            return Drink(id,name,image)
        }
        return Drink("","","")
    }
}