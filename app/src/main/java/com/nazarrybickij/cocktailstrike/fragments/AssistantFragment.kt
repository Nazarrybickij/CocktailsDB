package com.nazarrybickij.cocktailstrike.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nazarrybickij.cocktailstrike.viewmodels.AssistantViewModel
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.CocktailsAdapter
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.entity.Drink
import kotlinx.android.synthetic.main.assistant_fragment.view.*

class AssistantFragment : Fragment() {
    private var viewAdapter = CocktailsAdapter()
    private lateinit var callbackCocktailsAdapter: CocktailsAdapter.AdapterCallback
    lateinit var db:DBRepository

    companion object {
        fun newInstance() = AssistantFragment()
    }

    private lateinit var viewModel: AssistantViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            val topTitle = activity?.findViewById<TextView>(R.id.top_title)!!
            topTitle.text = "My Favourite"
            val topBar = activity?.findViewById<LinearLayout>(R.id.top_bar)!!
            topBar.visibility = LinearLayout.VISIBLE
        }catch (e: Exception){}
        return inflater.inflate(R.layout.assistant_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DBRepository.getInstance()
        callbackCocktailsAdapter = object :
            CocktailsAdapter.AdapterCallback {
            override fun onCocktailClick(id: String) {
                try {
                    val bundle = Bundle()
                    bundle.putInt("id", id.toInt())
                    view.findNavController().navigate(
                        R.id.action_assistantFragment_to_cocktailInfoFragment,
                        bundle
                    )
                } catch (e: Exception) {
                }
            }
            override fun onFavClick(drink: Drink) {
                db.insertOrDelete(drink)
                viewAdapter.inputValue(db.getAll() as MutableList<Drink>)
                viewAdapter.notifyDataSetChanged()
                setEmptyView(view)
            }
        }
        viewAdapter.inputValue(db.getAll() as MutableList<Drink>)
        viewAdapter.callback = callbackCocktailsAdapter
        view.list_fav.layoutManager = LinearLayoutManager(activity)
        view.list_fav.adapter = viewAdapter
        setEmptyView(view)
    }
    fun setEmptyView(view: View){
        if (viewAdapter.getValues().isEmpty()){
            view.list_fav.visibility = LinearLayout.GONE
            view.empty_view.visibility = LinearLayout.VISIBLE
            return
        }
        view.list_fav.visibility = LinearLayout.VISIBLE
        view.empty_view.visibility = LinearLayout.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AssistantViewModel::class.java)
    }

}