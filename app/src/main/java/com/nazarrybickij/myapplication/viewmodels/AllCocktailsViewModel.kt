package com.nazarrybickij.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.myapplication.adapters.CocktailsAdapter
import com.nazarrybickij.myapplication.adapters.IngredientsInFilterAdapter
import com.nazarrybickij.myapplication.entity.Drink
import com.nazarrybickij.myapplication.entity.FilterEntity
import com.nazarrybickij.myapplication.entity.ListCocktails
import com.nazarrybickij.myapplication.network.NetworkRepository

class AllCocktailsViewModel : ViewModel() {
    var lastposition = 0
    var lastSearchQuery = ""
    var lastDrinks = mutableListOf<Drink>()
    lateinit var cocktailsAdapter:CocktailsAdapter
    private val liveDataCocktails = MutableLiveData<ListCocktails>()
    private val networkRepository = NetworkRepository()
    private val listIngredients = MutableLiveData<ArrayList<String>>()
    var selectedList = ArrayList<String>()
    var mainFilter: FilterEntity = FilterEntity()
        set(value) {
            if (value == field) {
                return
            }
            field = value
            loadListCocktails()
        }

    fun getListCocktails(): MutableLiveData<ListCocktails> =  liveDataCocktails

    fun loadListCocktails(){
        networkRepository.loadCocktailsByFilter(liveDataCocktails, mainFilter)
    }

    fun loadIngredientsList() {
        if (listIngredients.value == null) {
            networkRepository.loadListIngredients(listIngredients)
        }
    }

    fun getListIngredients() = listIngredients

}