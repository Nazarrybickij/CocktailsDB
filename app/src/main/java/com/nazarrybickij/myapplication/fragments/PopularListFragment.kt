package com.nazarrybickij.myapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazarrybickij.myapplication.R
import com.nazarrybickij.myapplication.adapters.CocktailsFullinfoAdapter
import com.nazarrybickij.myapplication.entity.DrinkX
import kotlinx.android.synthetic.main.fragment_popular_list.view.*


class PopularListFragment : Fragment() {

    private  var list = mutableListOf<DrinkX>()
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
        }catch (e:Exception){
        }
        try {
            list = arguments?.getParcelableArrayList<DrinkX>("popular")!!
        }catch (e:Exception){
        }
        return inflater.inflate(R.layout.fragment_popular_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val popularListCallback = object :
            CocktailsFullinfoAdapter.AdapterCallback {
            override fun onCocktailClick(id: String) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id.toInt())
                    view.findNavController().navigate(R.id.action_popularListFragment_to_cocktailInfoFragment,bundle)
                } catch (e: Exception) {
                }
            }
        }
        var adapter = CocktailsFullinfoAdapter(list,popularListCallback)
        view.popular_list_view.layoutManager = LinearLayoutManager(context)
        view.popular_list_view.adapter = adapter
        view.back_view.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance() = PopularListFragment()
    }
}