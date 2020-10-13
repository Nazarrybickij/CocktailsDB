package com.nazarrybickij.myapplication.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nazarrybickij.myapplication.App
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkService private constructor() {
    private val mRetrofit: Retrofit
    val jSONApi: JSONPlaceHolderApi
        get() = mRetrofit.create(JSONPlaceHolderApi::class.java)

    companion object {
        // API Key
        private val API_KEY = "9973533"
        // Base URL
        var BASE_URL = "https://www.thecocktaildb.com/api/json/v2/$API_KEY/"
        // Search using name
        var COCKTAIL_SEARCH_URL_BY_NAME =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/search.php?s="
        // Search using id
        var COCKTAIL_SEARCH_URL_BY_ID =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/lookup.php?i="
        // Randomixer URL
        var COCKTAIL_URL_RANDOM =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/random.php"
        // Nav Drawer Filter URL
        var COCKTAIL_SEARCH_URL_INGREDIENT_GIN =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?i=Gin"
        var COCKTAIL_SEARCH_URL_INGREDIENT_VODKA =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?i=Vodka"
        var COCKTAIL_SEARCH_URL_ALCOHOLIC =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?a=Alcoholic"
        var COCKTAIL_SEARCH_URL_NON_ALCOHOLIC =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?a=Non_Alcoholic"
        var COCKTAIL_SEARCH_URL_COCKTAIL_GLASS =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?g=Cocktail_glass"
        var COCKTAIL_SEARCH_URL_COCKTAIL =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?c=Cocktail"
        var COCKTAIL_SEARCH_URL_ORDINARY =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?c=Ordinary_Drink"
        var COCKTAIL_SEARCH_URL_HIGHBALL_GLASS =
            "https://www.thecocktaildb.com/api/json/v2/$API_KEY/filter.php?g=Highball%20glass"
        // Ingredients URL
        var COCKTAIL_INGREDIENTS_URL = "https://www.thecocktaildb.com/images/ingredients/"
        var COCKTAIL_INGREDIENT_PNG_SMALL = "-Small.png"
        private var mInstance: NetworkService? = null
        val instance: NetworkService?
            get() {
                if (mInstance == null) {
                    mInstance =
                        NetworkService()
                }
                return mInstance
            }
    }

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(App.context!!.applicationContext))
            .build()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }



}