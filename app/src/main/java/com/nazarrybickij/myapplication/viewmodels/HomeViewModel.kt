package com.nazarrybickij.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.myapplication.SingleLiveEvent
import com.nazarrybickij.myapplication.entity.DrinkInfoList
import com.nazarrybickij.myapplication.network.NetworkRepository

class HomeViewModel : ViewModel() {
    private val networkRepository = NetworkRepository()
    private val mutableRandomCocktail = SingleLiveEvent<DrinkInfoList>()
    private val mutablePopularCocktails = MutableLiveData<DrinkInfoList>()

    fun getMutableRandomCocktail() = mutableRandomCocktail
    fun loadRandomCocktail(){
        networkRepository.loadRandomCocktails(mutableRandomCocktail)
    }
    fun getMutablePopularCocktails() = mutablePopularCocktails
    fun loadPopularCocktails(){
        networkRepository.loadPopularCocktails(mutablePopularCocktails)
    }
}