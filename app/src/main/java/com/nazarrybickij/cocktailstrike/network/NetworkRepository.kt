package com.nazarrybickij.cocktailstrike.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nazarrybickij.cocktailstrike.entity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NetworkRepository() {
    fun loadLatestDrinks(postLiveData: MutableLiveData<DrinkInfoList>) {
        NetworkService.instance?.jSONApi?.getLatestDrinksPost()?.enqueue(object :
            Callback<DrinkInfoList?> {
            override fun onResponse(
                call: Call<DrinkInfoList?>,
                response: Response<DrinkInfoList?>
            ) {
                val post = response.body()
                if (postLiveData.value != post) {
                    postLiveData.value = post }
            }

            override fun onFailure(call: Call<DrinkInfoList?>, t: Throwable) {
                call.clone()
            }
        })
    }
    fun loadListCocktailsByCategory(postLiveData: MutableLiveData<ListCocktails>,category: String) {
        NetworkService.instance?.jSONApi?.getPostByCategory(category)?.enqueue(object :
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
                Log.e("Network Error", t.localizedMessage!!)
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


    @Suppress("SENSELESS_COMPARISON")
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
                            Log.e("Error Network", t.localizedMessage!!)
                        }
                    })

            }

            override fun onFailure(call: Call<ListCocktails?>, t: Throwable) {
            }
        })
    }

    fun loadCocktailsByFilter(postLiveData: MutableLiveData<ListCocktails>, filter: FilterEntity) {
        if (filter.ingredients != ""){
            loadListCocktailsByIngredients(postLiveData, filter.ingredients)
            return
        }
        if (filter.alco != ""){
            loadListCocktailsByAlco(postLiveData, filter.alco)
            return
        }
        if (filter.category != ""){
            loadListCocktailsByCategory(postLiveData, filter.category)
            return
        }
        loadAllCocktails(postLiveData)
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
                Log.e("Error network", t.localizedMessage!!)
            }
        })
    }

}