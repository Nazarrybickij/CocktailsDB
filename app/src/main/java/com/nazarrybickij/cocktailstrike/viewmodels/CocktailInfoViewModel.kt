package com.nazarrybickij.cocktailstrike.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.cocktailstrike.SingleLiveEvent
import com.nazarrybickij.cocktailstrike.entity.DrinkInfoList
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import com.nazarrybickij.cocktailstrike.entity.IngredientEntity
import com.nazarrybickij.cocktailstrike.entity.IngredientsDescription
import com.nazarrybickij.cocktailstrike.network.NetworkRepository

class CocktailInfoViewModel : ViewModel() {
    private val liveDataCocktails = SingleLiveEvent<DrinkInfoList>()
    private val liveDataIngredientDesc = MutableLiveData<IngredientsDescription>()
    private val networkRepository = NetworkRepository()

    fun getLiveDataCocktails() = liveDataCocktails
    fun loadCocktailsById(id: Int) {
        networkRepository.loadCocktails(id, liveDataCocktails)
    }

    fun getLiveDataIngredientDesc() = liveDataIngredientDesc
    fun loadIngredientByName(s: String) {
        networkRepository.loadIngredientDescByName(liveDataIngredientDesc,s)
    }

    companion object {
        @Suppress("SENSELESS_COMPARISON")
        fun convertDrinkInfoInIngredientList(drinkX: DrinkX): MutableList<IngredientEntity> {
            var ingredientList = mutableListOf<IngredientEntity>()
            if (drinkX.strIngredient1 != null && drinkX.strIngredient1 != "") {
                if (drinkX.strMeasure1 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient1, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient1, drinkX.strMeasure1)
                    )
                }
            }

            if (drinkX.strIngredient2 != null && drinkX.strIngredient2 != "") {
                if (drinkX.strMeasure2 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient2, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient2, drinkX.strMeasure2)
                    )
                }
            }

            if (drinkX.strIngredient3 != null && drinkX.strIngredient3 != "") {
                if (drinkX.strMeasure3 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient3, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient3, drinkX.strMeasure3)
                    )
                }
            }

            if (drinkX.strIngredient4 != null && drinkX.strIngredient4 != "") {
                if (drinkX.strMeasure4 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient4, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient4, drinkX.strMeasure4)
                    )
                }
            }

            if (drinkX.strIngredient5 != null && drinkX.strIngredient5 != "") {
                if (drinkX.strMeasure5 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient5, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient5, drinkX.strMeasure5)
                    )
                }
            }

            if (drinkX.strIngredient6 != null && drinkX.strIngredient6 != "") {
                if (drinkX.strMeasure6 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient6, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient6, drinkX.strMeasure6)
                    )
                }
            }

            if (drinkX.strIngredient7 != null && drinkX.strIngredient7 != "") {
                if (drinkX.strMeasure7 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient7, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient7, drinkX.strMeasure7)
                    )
                }
            }

            if (drinkX.strIngredient8 != null && drinkX.strIngredient8 != "") {
                if (drinkX.strMeasure8 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient8, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient8, drinkX.strMeasure8)
                    )
                }
            }


            if (drinkX.strIngredient9 != null && drinkX.strIngredient9 != "") {
                if (drinkX.strMeasure9 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient9, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient9, drinkX.strMeasure9)
                    )
                }
            }


            if (drinkX.strIngredient10 != null && drinkX.strIngredient10 != "") {
                if (drinkX.strMeasure10 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient10, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient10, drinkX.strMeasure10)
                    )
                }
            }



            if (drinkX.strIngredient11 != null && drinkX.strIngredient11 != "") {
                if (drinkX.strMeasure11 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient11, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient11, drinkX.strMeasure11)
                    )
                }
            }


            if (drinkX.strIngredient12 != null && drinkX.strIngredient12 != "") {
                if (drinkX.strMeasure12 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient12, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient12, drinkX.strMeasure12)
                    )
                }
            }


            if (drinkX.strIngredient13 != null && drinkX.strIngredient13 != "") {
                if (drinkX.strMeasure13 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient13, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient13, drinkX.strMeasure13)
                    )
                }
            }


            if (drinkX.strIngredient14 != null && drinkX.strIngredient14 != "") {
                if (drinkX.strMeasure14 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient14, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient14, drinkX.strMeasure14)
                    )
                }
            }

            if (drinkX.strIngredient15 != null && drinkX.strIngredient15 != "") {
                if (drinkX.strMeasure15 == null) {
                    ingredientList.add(IngredientEntity(drinkX.strIngredient15, ""))
                } else {
                    ingredientList.add(
                        IngredientEntity(drinkX.strIngredient15, drinkX.strMeasure15)
                    )
                }
            }

            return ingredientList
        }
    }

}
