package com.nazarrybickij.cocktailstrike.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.CocktailsAdapterForSearch
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import com.nazarrybickij.cocktailstrike.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.search_fragment.view.*


class SearchFragment : DialogFragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CocktailsAdapterForSearch
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
            dialog.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.TOP)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CocktailsAdapterForSearch()
        var callbackCocktailsAdapter = object :
            CocktailsAdapterForSearch.AdapterCallback {
            override fun onCocktailClick(id: String) {
                val bundle = Bundle()
                bundle.putInt("id", id.toInt())
                findNavController().navigate(R.id.action_my_dialog_to_cocktailInfoFragment,bundle)
            }
        }
        var linearLayoutManager = LinearLayoutManager(context)
        viewModel.getMutableSearchCocktails().observe(viewLifecycleOwner, {
            adapter.inputValue(it.drinks as MutableList<DrinkX>)
        })
        view.main_recycler_search.layoutManager = linearLayoutManager
        adapter.callback = callbackCocktailsAdapter
        view.main_recycler_search.adapter = adapter
        view.back_image_view.setOnClickListener {
            dismiss()
        }
        view.main_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 1) {
                    viewModel.searchCocktail(newText)
                    if (dialog != null) {
                        val width = ViewGroup.LayoutParams.MATCH_PARENT
                        val height = ViewGroup.LayoutParams.MATCH_PARENT
                        dialog!!.window?.setLayout(width, height)
                    }
                    return false
                }
                if (dialog != null) {
                    val width = ViewGroup.LayoutParams.MATCH_PARENT
                    val height = ViewGroup.LayoutParams.WRAP_CONTENT
                    dialog!!.window?.setLayout(width, height)
                }
                adapter.clearValue()
                return false
            }

        })

    }
}