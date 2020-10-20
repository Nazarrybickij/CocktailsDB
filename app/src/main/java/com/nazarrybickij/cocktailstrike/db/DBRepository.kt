package com.nazarrybickij.cocktailstrike.db

import android.widget.Toast
import com.nazarrybickij.cocktailstrike.App
import com.nazarrybickij.cocktailstrike.entity.Drink

class DBRepository(var appRoomDB: AppRoomDB) {
    fun insert(drink: Drink) {
        appRoomDB.drinkDao().insertAll(drink)
        Toast.makeText(App.context,"Added to favorite",Toast.LENGTH_SHORT).show()
    }

    fun delete(drink: Drink) {
        appRoomDB.drinkDao().delete(drink)
        Toast.makeText(App.context,"Removed from favorite",Toast.LENGTH_SHORT).show()
    }

    fun getAll(): List<Drink> {
       return  appRoomDB.drinkDao().getAll()
    }

    fun isContain(id: Int): Boolean {
        val drinks = appRoomDB.drinkDao().getAll()
        for (listItem in drinks) {
            if (listItem.idDrink.toInt() == id) {
                return true
            }
        }
        return false
    }
    fun insertOrDelete(drink: Drink){
        if (isContain(drink.idDrink.toInt())){
            delete(drink)
            return
        }
        insert(drink)
    }

    companion object {
        private  var instance: DBRepository? = null
        fun getInstance(): DBRepository{
                if (instance == null) {
                    instance = DBRepository(App.database)
                }

                return instance as DBRepository
            }
    }
}