package com.nazarrybickij.myapplication.fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
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
import com.nazarrybickij.myapplication.viewmodels.HomeViewModel
import com.nazarrybickij.myapplication.R
import com.nazarrybickij.myapplication.adapters.CocktailsFullinfoAdapter
import com.nazarrybickij.myapplication.entity.DrinkX
import com.nazarrybickij.myapplication.entity.FilterEntity
import kotlinx.android.synthetic.main.include_popular.view.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    var popularList = mutableListOf<DrinkX>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        getPopularCocktails()
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
        }
        viewModel.getMutableRandomCocktail().observe(viewLifecycleOwner,{
            val bundle = Bundle()
            bundle.putParcelable("drink", it.drinks[0])
            view.findNavController().navigate(R.id.action_homeFragment_to_cocktailInfoFragment,bundle)
        })
        viewModel.getMutablePopularCocktails().observe(viewLifecycleOwner,{
            popularList = it.drinks as MutableList<DrinkX>
            var popularAdapter = CocktailsFullinfoAdapter(it.drinks.take(3),popularListCallback)
            val popularRecyclerView = view.popular_3_item_list
            popularRecyclerView.layoutManager = LinearLayoutManager(activity)
            popularRecyclerView.adapter = popularAdapter
        })
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
            view.findNavController().navigate(R.id.action_homeFragment_to_popularListFragment,bundle)
        }
    }

    private fun getPopularCocktails(){
        viewModel.loadPopularCocktails()
    }
}