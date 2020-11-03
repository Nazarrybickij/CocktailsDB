package com.nazarrybickij.cocktailstrike.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.cocktailstrike.App
import com.nazarrybickij.cocktailstrike.ControllerAds
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.CocktailsAdapter
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.nazarrybickij.cocktailstrike.entity.FilterEntity
import com.nazarrybickij.cocktailstrike.viewmodels.AllCocktailsViewModel
import kotlinx.android.synthetic.main.all_cocktails_fragment.view.*


class AllCocktailsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CocktailsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var callbackCocktailsAdapter: CocktailsAdapter.AdapterCallback
    private var drinks: MutableList<Drink>? = null
    private lateinit var filterBottomSheetFragment: FilterBottomSheetFragment
    private lateinit var searchView: SearchView
    private lateinit var viewModel: AllCocktailsViewModel
    private var filterEntity: FilterEntity? = null

    companion object {
        fun newInstance() = AllCocktailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val i = inflater.inflate(R.layout.all_cocktails_fragment, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(AllCocktailsViewModel::class.java)
        try {
            val topTitle = activity?.findViewById<TextView>(R.id.top_title)!!
            topTitle.text = App.getResources.getString(R.string.all_cocktail_frag_title)
            val topBar = activity?.findViewById<LinearLayout>(R.id.top_bar)!!
            topBar.visibility = LinearLayout.VISIBLE
        } catch (e: Exception) {
        }
        try {
            filterEntity = arguments?.getParcelable<FilterEntity>("filter")!!
            viewModel.mainFilter = filterEntity as FilterEntity
            if (filterEntity!!.ingredients.isNotEmpty()) {
                val selList = ArrayList<String>()
                selList.add(filterEntity!!.ingredients)
                viewModel.selectedList = selList
            }else{viewModel.selectedList = ArrayList<String>()}

        } catch (e: Exception) {
        }
        if (viewModel.lastDrinks.isEmpty() || filterEntity != null) {
            viewModel.loadListCocktails()
            viewModel.loadIngredientsList()
            i.progress_bar_view.visibility = LinearLayout.VISIBLE
        }
        retainInstance = true
        filterBottomSheetFragment = FilterBottomSheetFragment()
        return i
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.searchView
        recyclerView = view.findViewById<RecyclerView>(R.id.allCocktails_recycler_view)
        viewManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = viewManager
        drinks = viewModel.lastDrinks
        if (ControllerAds.show){
            val controllerInterstitialAd = ControllerAds.getInstance().getMInterstitialAd()
            if (controllerInterstitialAd.isLoaded){
                controllerInterstitialAd.show()
                ControllerAds.show = false
            }
        }
        callbackCocktailsAdapter = object :
            CocktailsAdapter.AdapterCallback {
            override fun onCocktailClick(id: String) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id.toInt())
                    view.findNavController().navigate(
                        R.id.action_allCocktailsFragment_to_cocktailInfoFragment,
                        bundle
                    )
                } catch (e: Exception) {
                }
            }

            override fun onFavClick(drink: Drink) {
                val dbRepository = DBRepository.getInstance()
                dbRepository.insertOrDelete(drink)
                viewAdapter.notifyDataSetChanged()
            }
        }
        if (drinks!!.isEmpty()) {
            viewAdapter = CocktailsAdapter()
        } else {
            viewAdapter = viewModel.cocktailsAdapter
            recyclerView.adapter = viewAdapter
            recyclerView.scrollToPosition(viewModel.lastposition)
            searchView.setQuery(viewModel.lastSearchQuery, true)
            view.main_linear_view.visibility = LinearLayout.VISIBLE
            view.progress_bar_view.visibility = LinearLayout.GONE
        }
        viewModel.getListCocktails().observe(viewLifecycleOwner, Observer { it ->
            if (drinks != it.drinks) {
                filterEntity = null
                arguments?.remove("filter")
                drinks = it.drinks as MutableList<Drink>
                viewAdapter.inputValue(drinks!!)
                recyclerView.adapter = viewAdapter
                searchView.setQuery("", true)
                searchView.clearFocus()
            }
            if (drinks!!.isEmpty()) {
                view.empty_text_view.visibility = LinearLayout.VISIBLE
                view.empty_text_view.text = "No Result"
            } else {
                view.empty_text_view.visibility = LinearLayout.GONE
            }
            view.main_linear_view.visibility = LinearLayout.VISIBLE
            view.progress_bar_view.visibility = LinearLayout.GONE
            viewAdapter.callback = callbackCocktailsAdapter
        })
        view.fab.setOnClickListener {
            filterBottomSheetFragment.show(parentFragmentManager, "filter")
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewAdapter.filter.filter(newText)
                if (newText != null) {
                    viewModel.lastSearchQuery = newText
                }
                return false
            }
        })

    }

    override fun onPause() {
        super.onPause()
        try {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            viewModel.lastposition = layoutManager.findFirstVisibleItemPosition()
            viewModel.cocktailsAdapter = viewAdapter
            viewModel.lastDrinks = drinks!!
        } catch (e: Exception) {
        }
    }

}