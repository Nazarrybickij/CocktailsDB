package com.nazarrybickij.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.nazarrybickij.myapplication.SingleLiveEvent
import com.nazarrybickij.myapplication.entity.DrinkInfoList
import com.nazarrybickij.myapplication.entity.DrinkX
import com.nazarrybickij.myapplication.entity.IngredientEntity
import com.nazarrybickij.myapplication.network.NetworkRepository
import java.lang.Exception

class CocktailInfoViewModel : ViewModel() {
    private val liveDataCocktails = SingleLiveEvent<DrinkInfoList>()
    private val networkRepository = NetworkRepository()

    fun getLiveDataCocktails() = liveDataCocktails
    fun getCocktailsById(id: Int) {
        networkRepository.loadCocktails(id,liveDataCocktails)
    }

    companion object{
        fun convertDrinkInfoInIngredientList(drinkX: DrinkX): MutableList<IngredientEntity> {
            var ingredientList = mutableListOf<IngredientEntity>()
            try {
                if (drinkX.strIngredient1 != null && drinkX.strIngredient1 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient1,drinkX.strMeasure1)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient2 != null && drinkX.strIngredient2 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient2,drinkX.strMeasure2)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient3 != null && drinkX.strIngredient3 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient3,drinkX.strMeasure3)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient4 != null && drinkX.strIngredient4 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient4,drinkX.strMeasure4)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient5 != null && drinkX.strIngredient5 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient5,drinkX.strMeasure5)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient6 != null && drinkX.strIngredient6 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient6,drinkX.strMeasure6)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient7 != null && drinkX.strIngredient7 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient7,drinkX.strMeasure7)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient8 != null && drinkX.strIngredient8 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient8,drinkX.strMeasure8)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient9 != null && drinkX.strIngredient9 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient9,drinkX.strMeasure9)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient10 != null && drinkX.strIngredient10 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient10,drinkX.strMeasure10)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient11 != null && drinkX.strIngredient11 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient11,drinkX.strMeasure11)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient12 != null && drinkX.strIngredient12 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient12,drinkX.strMeasure12)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient13 != null && drinkX.strIngredient13 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient13,drinkX.strMeasure13)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient14 != null && drinkX.strIngredient14 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient14,drinkX.strMeasure14)
                )}
            }catch (e:Exception){

            }
            try {
                if (drinkX.strIngredient15 != null && drinkX.strIngredient15 != ""){ingredientList.add(
                    IngredientEntity(drinkX.strIngredient15,drinkX.strMeasure15)
                )}
            }catch (e:Exception){

            }
            return ingredientList
        }
    }

    }
