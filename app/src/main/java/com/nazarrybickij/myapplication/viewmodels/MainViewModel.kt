package com.nazarrybickij.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.myapplication.entity.DrinkInfoList
import com.nazarrybickij.myapplication.network.NetworkRepository

class MainViewModel: ViewModel() {
    private val networkRepository = NetworkRepository()
    private val mutableSearchCocktails = MutableLiveData<DrinkInfoList>()

    fun getMutableSearchCocktails() = mutableSearchCocktails
    fun searchCocktail(s:String){
        networkRepository.loadSearchCocktailsByName(s,mutableSearchCocktails)
    }
}