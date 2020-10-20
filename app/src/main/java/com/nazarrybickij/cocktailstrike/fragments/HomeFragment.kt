package com.nazarrybickij.cocktailstrike.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazarrybickij.cocktailstrike.viewmodels.HomeViewModel
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.CategoriesDrinkAdapter
import com.nazarrybickij.cocktailstrike.adapters.CocktailsFullinfoAdapter
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.db.LastDrinkSP
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import com.nazarrybickij.cocktailstrike.entity.FilterEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.include_categories.view.*
import kotlinx.android.synthetic.main.include_latest_drinks_in_database.view.*
import kotlinx.android.synthetic.main.include_popular.view.*

class HomeFragment : Fragment() {

    private var popularAdapter = CocktailsFullinfoAdapter()
    private var latestDrinksAdapter = CocktailsFullinfoAdapter()
    private lateinit var viewModel: HomeViewModel
    var popularList = mutableListOf<DrinkX>()
    private lateinit var lastDrinkSP: LastDrinkSP
    private var lastDrink:Drink? = null
    var latestDrinksList = mutableListOf<DrinkX>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        lastDrinkSP = LastDrinkSP.getInstance()
        viewModel.loadPopularCocktails()
        viewModel.loadLatestDrinks()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        try {
            val topTitle = activity?.findViewById<TextView>(R.id.top_title)!!
            topTitle.text = "What do you want to drink today?"
            val topBar = activity?.findViewById<LinearLayout>(R.id.top_bar)!!
            topBar.visibility = LinearLayout.VISIBLE
        }catch (e:Exception){
        }
        lastDrink = lastDrinkSP.getLastDrink()
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val popularListCallback = object :
            CocktailsFullinfoAdapter.AdapterCallback {
            override fun onCocktailClick(id: String) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id.toInt())
                    view.findNavController().navigate(R.id.action_homeFragment_to_cocktailInfoFragment,bundle)
                } catch (e: Exception) {
                }
            }

            override fun onFavClick(drink: Drink) {
                val dbRepository = DBRepository.getInstance()
                dbRepository.insertOrDelete(drink)
                popularAdapter.notifyDataSetChanged()
            }
        }
        val latestDrinksCallback = object :
            CocktailsFullinfoAdapter.AdapterCallback {
            override fun onCocktailClick(id: String) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id.toInt())
                    view.findNavController().navigate(R.id.action_homeFragment_to_cocktailInfoFragment,bundle)
                } catch (e: Exception) {
                }
            }

            override fun onFavClick(drink: Drink) {
                val dbRepository = DBRepository.getInstance()
                dbRepository.insertOrDelete(drink)
                latestDrinksAdapter.notifyDataSetChanged()
            }
        }

        viewModel.getMutableRandomCocktail().observe(viewLifecycleOwner,{
            val bundle = Bundle()
            bundle.putParcelable("drink", it.drinks[0])
            view.findNavController().navigate(R.id.action_homeFragment_to_cocktailInfoFragment,bundle)
        })
        viewModel.getMutablePopularCocktails().observe(viewLifecycleOwner,{
            popularList = it.drinks as MutableList<DrinkX>
            popularAdapter.values = it.drinks.take(3) as MutableList<DrinkX>
            popularAdapter.callback = popularListCallback
            val popularRecyclerView = view.popular_3_item_list
            popularRecyclerView.layoutManager = LinearLayoutManager(activity)
            popularRecyclerView.adapter = popularAdapter
            view.popular_progress_bar.visibility = LinearLayout.GONE
            popularRecyclerView.visibility = LinearLayout.VISIBLE
        })
        viewModel.getMutableLatestDrinksInBase().observe(viewLifecycleOwner,{
            latestDrinksList = it.drinks as MutableList<DrinkX>
            latestDrinksAdapter.values = it.drinks.take(3) as MutableList<DrinkX>
            latestDrinksAdapter.callback = latestDrinksCallback
            val latestDrinksRecyclerView = view.latest_drinks_in_base_3_item_list
            latestDrinksRecyclerView.layoutManager = LinearLayoutManager(activity)
            latestDrinksRecyclerView.adapter = latestDrinksAdapter
            view.latest_drinks_in_base_progress_bar.visibility = LinearLayout.GONE
            latestDrinksRecyclerView.visibility = LinearLayout.VISIBLE
        })
        view.last_cocktail_view.apply {
            if (lastDrink!!.idDrink.isNotEmpty()){
                Picasso.with(view.context)
                    .load(lastDrink!!.strDrinkThumb)
                    .into(last_cocktail_image)
                last_cocktail_name.text = lastDrink!!.strDrink
            }
            setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", lastDrink!!.idDrink.toInt())
                view.findNavController().navigate(R.id.action_homeFragment_to_cocktailInfoFragment,bundle)
            }
        }
        view.list_categories_drink.apply {
            val categoriesAdapter = CategoriesDrinkAdapter()
            val categoryCallback = object:CategoriesDrinkAdapter.AdapterCallback {
                override fun onCategoriesClick(category: String) {
                    val bundle = Bundle()
                    bundle.putParcelable("filter", FilterEntity(category = category))
                    view.findNavController().navigate(R.id.action_homeFragment_to_allCocktailsFragment,bundle)
                }
            }
            categoriesAdapter.values = viewModel.getListCategoriesDrink()
            categoriesAdapter.callback = categoryCallback
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
        view.findViewById<CardView>(R.id.random_view).setOnClickListener {
            viewModel.loadRandomCocktail()
        }
        view.findViewById<CardView>(R.id.non_alcoholic_view).setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("filter", FilterEntity(alco = "Non_alcoholic"))
            view.findNavController().navigate(R.id.action_homeFragment_to_allCocktailsFragment,bundle)
        }
        view.button_all_popular_list.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("popular", ArrayList(popularList))
            bundle.putString("title","Popular")
            view.findNavController().navigate(R.id.action_homeFragment_to_popularListFragment,bundle)
        }
        view.button_all_latest_drink_in_base.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("popular", ArrayList(latestDrinksList))
            bundle.putString("title","Latest Drinks")
            view.findNavController().navigate(R.id.action_homeFragment_to_popularListFragment,bundle)
        }


    }
    companion object {
        fun newInstance() = HomeFragment()
    }
}