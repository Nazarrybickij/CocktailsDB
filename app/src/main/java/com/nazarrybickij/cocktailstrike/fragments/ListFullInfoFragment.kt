package com.nazarrybickij.cocktailstrike.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.CocktailsFullinfoAdapter
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import kotlinx.android.synthetic.main.fragment_list_full_info_drinks.view.*



class ListFullInfoFragment : Fragment() {
    private var adapter = CocktailsFullinfoAdapter()
    private var list = mutableListOf<DrinkX>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            val topBar = activity?.findViewById<LinearLayout>(R.id.top_bar)!!
            topBar.visibility = LinearLayout.GONE
        } catch (e: Exception) {
        }
        try {
            list = arguments?.getParcelableArrayList<DrinkX>("popular")!!
        } catch (e: Exception) {
        }
        return inflater.inflate(R.layout.fragment_list_full_info_drinks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.title_top_view.text = arguments?.getString("title", "")
        val popularListCallback = object :
            CocktailsFullinfoAdapter.AdapterCallback {
            override fun onCocktailClick(id: String) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id.toInt())
                    view.findNavController()
                        .navigate(R.id.action_popularListFragment_to_cocktailInfoFragment, bundle)
                } catch (e: Exception) {
                }
            }

            override fun onFavClick(drink: Drink) {
                val dbRepository = DBRepository.getInstance()
                dbRepository.insertOrDelete(drink)
                adapter.notifyDataSetChanged()
            }
        }
        adapter.values = list
        adapter.callback = popularListCallback
        view.popular_list_view.layoutManager = LinearLayoutManager(context)
        view.popular_list_view.adapter = adapter
        view.back_view.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance() = ListFullInfoFragment()
    }
}