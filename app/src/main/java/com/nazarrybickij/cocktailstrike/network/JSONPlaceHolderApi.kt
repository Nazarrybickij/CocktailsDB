package com.nazarrybickij.cocktailstrike.network

import com.nazarrybickij.cocktailstrike.entity.DrinkInfoList
import com.nazarrybickij.cocktailstrike.entity.IngredientsDescription
import com.nazarrybickij.cocktailstrike.entity.IngredientsList
import com.nazarrybickij.cocktailstrike.entity.ListCocktails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface JSONPlaceHolderApi {
    @GET("filter.php")
    fun getPostByAlcoholic(@Query("a") a:String): Call<ListCocktails?>?
    @GET("filter.php")
    fun getPostByCategory(@Query("c") filter:String): Call<ListCocktails?>?
    @GET("lookup.php")
    fun getPostById(@Query("i") id:Int): Call<DrinkInfoList?>?
    @GET("random.php")
    fun getRandomPost(): Call<DrinkInfoList?>?
    @GET("popular.php")
    fun getPopularPost(): Call<DrinkInfoList?>?
    @GET("filter.php")
    fun getPostByMultiIngredient(@Query("i") i:String): Call<ListCocktails?>?
    @GET("list.php")
    fun getPostListIngredients(@Query("i") i:String): Call<IngredientsList?>?
    @GET("search.php")
    fun getPostSearchCocktail(@Query("s") s:String): Call<DrinkInfoList?>?
    @GET("search.php")
    fun getPostSearchIngredient(@Query("i") i:String): Call<IngredientsDescription?>?
    @GET("latest.php")
    fun getLatestDrinksPost(): Call<DrinkInfoList?>?

}