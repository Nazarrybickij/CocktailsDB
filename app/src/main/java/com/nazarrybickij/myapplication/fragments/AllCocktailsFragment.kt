package com.nazarrybickij.myapplication.fragments


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.nazarrybickij.myapplication.App
import com.nazarrybickij.myapplication.R
import com.nazarrybickij.myapplication.adapters.CocktailsAdapter
import com.nazarrybickij.myapplication.entity.Drink
import com.nazarrybickij.myapplication.entity.FilterEntity
import com.nazarrybickij.myapplication.viewmodels.AllCocktailsViewModel
import kotlinx.android.synthetic.main.all_cocktails_fragment.view.*
import kotlinx.android.synthetic.main.item_cocktails.view.*
import java.text.FieldPosition


class AllCocktailsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CocktailsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var callbackCocktailsAdapter: CocktailsAdapter.AdapterCallback
    private var drinks: MutableList<Drink>? = null
    private lateinit var filterBottomSheetFragment: FilterBottomSheetFragment
    private lateinit var searchView: SearchView
    private lateinit var viewModel: AllCocktailsViewModel
    private var filterEntity:FilterEntity? = null

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
        viewModel = ViewModelProvider(requireActivity()).get(AllCocktailsViewModel::class.java)
        try {
            val topTitle = activity?.findViewById<TextView>(R.id.top_title)!!
            topTitle.text = "All cocktails"
            val topBar = activity?.findViewById<LinearLayout>(R.id.top_bar)!!
            topBar.visibility = LinearLayout.VISIBLE
        }catch (e: Exception){}
        try {
            filterEntity = arguments?.getParcelable<FilterEntity>("filter")!!
            viewModel.mainFilter = filterEntity as FilterEntity
            if (viewModel.selectedList.isNotEmpty()){
                viewModel.selectedList = ArrayList<String>()
            }
        }catch (e:Exception){}
        if (viewModel.lastDrinks.isEmpty() || filterEntity != null){
            viewModel.loadListCocktails()
            viewModel.loadIngredientsList()
        }
        retainInstance = true
        filterBottomSheetFragment = FilterBottomSheetFragment()
        return inflater.inflate(R.layout.all_cocktails_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = view.searchView
        recyclerView = view.findViewById<RecyclerView>(R.id.allCocktails_recycler_view)
        viewManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = viewManager
        drinks = viewModel.lastDrinks
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
        }
        if (drinks!!.isEmpty()){
            viewAdapter = CocktailsAdapter()
        }else{
            viewAdapter = viewModel.cocktailsAdapter
            recyclerView.adapter = viewAdapter
            recyclerView.scrollToPosition(viewModel.lastposition)
            searchView.setQuery(viewModel.lastSearchQuery, true)
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
        }catch (e: Exception){}
    }

}