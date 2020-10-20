package com.nazarrybickij.cocktailstrike.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.SingleLiveEvent
import com.nazarrybickij.cocktailstrike.entity.CategoriesEntity
import com.nazarrybickij.cocktailstrike.entity.DrinkInfoList
import com.nazarrybickij.cocktailstrike.network.NetworkRepository

class HomeViewModel : ViewModel() {
    private val networkRepository = NetworkRepository()
    private val mutableRandomCocktail = SingleLiveEvent<DrinkInfoList>()
    private val mutablePopularCocktails = MutableLiveData<DrinkInfoList>()
    private val mutableLatestDrinksInBase = MutableLiveData<DrinkInfoList>()

    fun getMutableRandomCocktail() = mutableRandomCocktail
    fun loadRandomCocktail(){
        networkRepository.loadRandomCocktails(mutableRandomCocktail)
    }
    fun getMutablePopularCocktails() = mutablePopularCocktails
    fun loadPopularCocktails(){
        networkRepository.loadPopularCocktails(mutablePopularCocktails)
    }

    fun getMutableLatestDrinksInBase() = mutableLatestDrinksInBase
    fun loadLatestDrinks(){
        networkRepository.loadLatestDrinks(mutableLatestDrinksInBase)
    }

    fun getListCategoriesDrink():MutableList<CategoriesEntity>{
        val mutableListCategory = mutableListOf<CategoriesEntity>()
        mutableListCategory.add(CategoriesEntity("Ordinary Drink", R.drawable.ordinary))
        mutableListCategory.add(CategoriesEntity("Cocktail",R.drawable.cocktais))
        mutableListCategory.add(CategoriesEntity("Milk / Float / Shake",R.drawable.milkshake))
        mutableListCategory.add(CategoriesEntity("Cocoa",R.drawable.cocoa))
        mutableListCategory.add(CategoriesEntity("Shot",R.drawable.shot))
        mutableListCategory.add(CategoriesEntity("Coffee / Tea",R.drawable.coffetea))
        mutableListCategory.add(CategoriesEntity("Homemade Liqueur",R.drawable.homemadeliqueur))
        mutableListCategory.add(CategoriesEntity("Punch / Party Drink",R.drawable.punch))
        mutableListCategory.add(CategoriesEntity("Beer",R.drawable.bear))
        mutableListCategory.add(CategoriesEntity("Soft Drink / Soda",R.drawable.soda))
        return mutableListCategory
    }
}