package com.nazarrybickij.cocktailstrike.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.cocktailstrike.viewmodels.CocktailInfoViewModel
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.IngredientAdapter
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.db.LastDrinkSP
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import com.nazarrybickij.cocktailstrike.viewmodels.CocktailInfoViewModel.Companion.convertDrinkInfoInIngredientList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cocktail_info_fragment.view.*
import kotlinx.android.synthetic.main.include_title_for_info.view.*

class CocktailInfoFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var idCocktail:Int? = null
    private var cocktail: DrinkX? = null
    private lateinit var lastDrinkSP:LastDrinkSP

    companion object {
        fun newInstance() = CocktailInfoFragment()
    }

    private lateinit var viewModel: CocktailInfoViewModel

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
        lastDrinkSP = LastDrinkSP.getInstance()
        viewModel = ViewModelProvider(requireActivity()).get(CocktailInfoViewModel::class.java)
        return inflater.inflate(R.layout.cocktail_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState == null){
            try {
                idCocktail = requireArguments().getInt("id",0)
            }catch (e:Exception){
            }
            try {
                cocktail = requireArguments().getParcelable("drink")
            }catch (e:Exception){
            }
        }else{
            cocktail = viewModel.getLiveDataCocktails().value?.drinks?.get(0)
        }
        viewManager = LinearLayoutManager(view.context)
        recyclerView = view.findViewById<RecyclerView>(R.id.ingredient_list_view).apply {
            layoutManager = viewManager
        }
        if (idCocktail != 0 && idCocktail != null){
            loadInfo(view)
        }else{
            setInfo(view)
        }
        viewModel.getLiveDataCocktails().observe(viewLifecycleOwner, { it ->
            cocktail = it.drinks[0]
            setInfo(view)
        })
        view.findViewById<ImageView>(R.id.back_view).setOnClickListener {
            activity?.onBackPressed()
        }
        view.findViewById<ImageView>(R.id.fav).setOnClickListener {
            if (cocktail != null) {
                val dbRepository = DBRepository.getInstance()
                dbRepository.insertOrDelete(convertDrinkXToDrink(cocktail!!))
                setFav(view)
            }
        }

    }
    private fun loadInfo(view: View){
        view.progress_bar_view.visibility = LinearLayout.VISIBLE
        if (viewModel.getLiveDataCocktails().value?.drinks?.get(0)?.idDrink?.toInt() != idCocktail){
            viewModel.getCocktailsById(idCocktail!!)
        }else{
            cocktail = viewModel.getLiveDataCocktails().value?.drinks?.get(0)
            setInfo(view)
        }

    }
    private fun setInfo(view: View){
        try {
            lastDrinkSP.saveLastDrink(convertDrinkXToDrink(cocktail!!))
            view.title_top_view.text = cocktail?.strDrink
            view.title_under_view.text = cocktail?.strDrink
            view.category_under_title_view.text = cocktail?.strCategory
            view.alco_under_title_view.text = cocktail?.strAlcoholic
            Picasso.with(view.context).load(cocktail?.strDrinkThumb).error(R.drawable.nophoto).into(view.image_cocktail_in_info)
            view.instruction_view.text = cocktail?.strInstructions
            setFav(view)
            var listIngredients = convertDrinkInfoInIngredientList(cocktail!!)
            if (listIngredients.isNotEmpty()){
                viewAdapter = IngredientAdapter(listIngredients)
                recyclerView.addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
                recyclerView.adapter = viewAdapter
            }else{
                view.ingredient_textView.visibility = LinearLayout.GONE
            }
            view.progress_bar_view.visibility = LinearLayout.GONE
            view.scroll_view.visibility = LinearLayout.VISIBLE
        }catch (e:Exception){
            activity?.onBackPressed()
        }
    }
    private fun setFav(view: View){
        var db = DBRepository.getInstance()
        if (cocktail != null){
            if (db.isContain(cocktail!!.idDrink.toInt())){
                view.fav.setImageResource(R.drawable.star_selected_icon)
                return
            }
            view.fav.setImageResource(R.drawable.star_icon)
        }

    }
    fun convertDrinkXToDrink(drinkX: DrinkX): Drink {
        return Drink(drinkX.idDrink,drinkX.strDrink,drinkX.strDrinkThumb)
    }
}