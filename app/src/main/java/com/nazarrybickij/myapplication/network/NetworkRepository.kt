package com.nazarrybickij.myapplication.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nazarrybickij.myapplication.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepository() {
    fun loadListCocktailsByCategory(postLiveData: MutableLiveData<ListCocktails>) {
        NetworkService.instance?.jSONApi?.getPostByCategory("Cocktail")?.enqueue(object :
            Callback<ListCocktails?> {
            override fun onResponse(
                call: Call<ListCocktails?>,
                response: Response<ListCocktails?>
            ) {
                val post = response.body()
                if (post != null) {
                }
                if (postLiveData.value != post) {
                    postLiveData.value = post
                }

            }

            override fun onFailure(call: Call<ListCocktails?>, t: Throwable) {
            }
        })
    }

    fun loadListCocktailsByIngredients(postLiveData: MutableLiveData<ListCocktails>, i: String) {
        NetworkService.instance?.jSONApi?.getPostByMultiIngredient(i)?.enqueue(object :
            Callback<ListCocktails?> {
            override fun onResponse(
                call: Call<ListCocktails?>,
                response: Response<ListCocktails?>
            ) {
                val post = response.body()
                if (postLiveData.value != post) {
                    postLiveData.value = post
                }

            }

            override fun onFailure(call: Call<ListCocktails?>, t: Throwable) {
                Log.e("Network Error", t.message)
                postLiveData.value = ListCocktails(mutableListOf<Drink>())
            }
        })
    }

    fun loadCocktails(id: Int, postLiveData: MutableLiveData<DrinkInfoList>) {
        NetworkService.instance?.jSONApi?.getPostById(id)?.enqueue(object :
            Callback<DrinkInfoList?> {
            override fun onResponse(
                call: Call<DrinkInfoList?>,
                response: Response<DrinkInfoList?>
            ) {
                val post = response.body()
                if (postLiveData.value != post) {
                    postLiveData.value = post
                }

            }

            override fun onFailure(call: Call<DrinkInfoList?>, t: Throwable) {
            }
        })
    }

    fun loadSearchCocktailsByName(s: String, postLiveData: MutableLiveData<DrinkInfoList>) {
        NetworkService.instance?.jSONApi?.getPostSearchCocktail(s)?.enqueue(object :
            Callback<DrinkInfoList?> {
            override fun onResponse(
                call: Call<DrinkInfoList?>,
                response: Response<DrinkInfoList?>
            ) {
                val post = response.body()
                if (post != null && post.drinks != null) {
                        postLiveData.value = post
                    return
                }
                postLiveData.value = DrinkInfoList(mutableListOf<DrinkX>())
            }

            override fun onFailure(call: Call<DrinkInfoList?>, t: Throwable) {
            }
        })
    }

    fun loadRandomCocktails(postLiveData: MutableLiveData<DrinkInfoList>) {
        NetworkService.instance?.jSONApi?.getRandomPost()?.enqueue(object :
            Callback<DrinkInfoList?> {
            override fun onResponse(
                call: Call<DrinkInfoList?>,
                response: Response<DrinkInfoList?>
            ) {
                val post = response.body()
                if (postLiveData.value != post) {
                    postLiveData.value = post
                }

            }

            override fun onFailure(call: Call<DrinkInfoList?>, t: Throwable) {
            }
        })
    }

    fun loadPopularCocktails(postLiveData: MutableLiveData<DrinkInfoList>) {
        NetworkService.instance?.jSONApi?.getPopularPost()?.enqueue(object :
            Callback<DrinkInfoList?> {
            override fun onResponse(
                call: Call<DrinkInfoList?>,
                response: Response<DrinkInfoList?>
            ) {
                val post = response.body()
                if (postLiveData.value != post) {
                    postLiveData.value = post
                }

            }

            override fun onFailure(call: Call<DrinkInfoList?>, t: Throwable) {
            }
        })
    }

    fun loadListCocktailsByAlco(postLiveData: MutableLiveData<ListCocktails>, a: String) {
        NetworkService.instance?.jSONApi?.getPostByAlcoholic(a)?.enqueue(object :
            Callback<ListCocktails?> {
            override fun onResponse(
                call: Call<ListCocktails?>,
                response: Response<ListCocktails?>
            ) {
                val post = response.body()
                if (post != null) {
                }
                if (postLiveData.value != post) {
                    postLiveData.value = post
                }

            }

            override fun onFailure(call: Call<ListCocktails?>, t: Throwable) {
            }
        })
    }

    fun loadAllCocktails(postLiveData: MutableLiveData<ListCocktails>) {
        NetworkService.instance?.jSONApi?.getPostByAlcoholic("Alcoholic")?.enqueue(object :
            Callback<ListCocktails?> {
            override fun onResponse(
                call: Call<ListCocktails?>,
                response: Response<ListCocktails?>
            ) {
                var listCocktails = response.body()
                NetworkService.instance?.jSONApi?.getPostByAlcoholic("Non_Alcoholic")
                    ?.enqueue(object :
                        Callback<ListCocktails?> {
                        override fun onResponse(
                            call: Call<ListCocktails?>,
                            response: Response<ListCocktails?>
                        ) {
                            var mutlist1 = listCocktails?.drinks as MutableList
                            var mutlist2 = response.body()?.drinks as MutableList
                            mutlist1.addAll(mutlist2)
                            postLiveData.value = ListCocktails(mutlist1)

                        }

                        override fun onFailure(call: Call<ListCocktails?>, t: Throwable) {
                            Log.e("Error Network", t.message)
                        }
                    })

            }

            override fun onFailure(call: Call<ListCocktails?>, t: Throwable) {
            }
        })
    }

    fun loadCocktailsByFilter(postLiveData: MutableLiveData<ListCocktails>, filter: FilterEntity) {
        if (filter.ingredients == "") {
            if (filter.alco == "") {
                loadAllCocktails(postLiveData)
                return
            }
            loadListCocktailsByAlco(postLiveData, filter.alco)
            return
        }
        Log.d("load", filter.ingredients)
        loadListCocktailsByIngredients(postLiveData, filter.ingredients)

    }

    fun loadListIngredients(postLiveData: MutableLiveData<ArrayList<String>>) {
        NetworkService.instance?.jSONApi?.getPostListIngredients("list")?.enqueue(object :
            Callback<IngredientsList?> {
            override fun onResponse(
                call: Call<IngredientsList?>,
                response: Response<IngredientsList?>
            ) {
                val post = response.body()
                var listI = ArrayList<String>()
                if (post != null) {
                    for (i in post.drinks) {
                        listI.add(i.strIngredient1)
                    }
                    postLiveData.value = listI
                }

            }

            override fun onFailure(call: Call<IngredientsList?>, t: Throwable) {
                Log.e("Error network", t.message)
            }
        })
    }

}