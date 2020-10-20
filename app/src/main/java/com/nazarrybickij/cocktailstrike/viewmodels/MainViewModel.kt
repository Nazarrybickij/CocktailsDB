package com.nazarrybickij.cocktailstrike.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nazarrybickij.cocktailstrike.entity.DrinkInfoList
import com.nazarrybickij.cocktailstrike.network.NetworkRepository

class MainViewModel : ViewModel() {
    private val networkRepository = NetworkRepository()
    private val mutableSearchCocktails = MutableLiveData<DrinkInfoList>()

    fun getMutableSearchCocktails() = mutableSearchCocktails
    fun searchCocktail(s: String) {
        networkRepository.loadSearchCocktailsByName(s, mutableSearchCocktails)
    }


}
