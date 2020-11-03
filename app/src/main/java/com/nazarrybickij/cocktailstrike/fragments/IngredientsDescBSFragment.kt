package com.nazarrybickij.cocktailstrike.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.adapters.IngredientsInFilterAdapter
import com.nazarrybickij.cocktailstrike.entity.FilterEntity
import com.nazarrybickij.cocktailstrike.network.NetworkService
import com.nazarrybickij.cocktailstrike.viewmodels.AllCocktailsViewModel
import com.nazarrybickij.cocktailstrike.viewmodels.CocktailInfoViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import kotlinx.android.synthetic.main.fragment_ingredients_description.view.*

class IngredientsDescBSFragment : BottomSheetDialogFragment() {
    private var mBehavior: BottomSheetBehavior<*>? = null
    private lateinit var viewModel: CocktailInfoViewModel
    private lateinit var viewDialog: View
    private lateinit var ingredientName:String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        viewDialog = View.inflate(context, R.layout.fragment_ingredients_description, null)
        dialog.setContentView(viewDialog)
        mBehavior = BottomSheetBehavior.from<View>(viewDialog.parent as View)
        try {
            ingredientName = arguments?.getString("ingredient","vodka")!!
        }catch (e:Exception){
            ingredientName = "vodka"
        }
        viewModel.loadIngredientByName(ingredientName)

        viewModel.getLiveDataIngredientDesc().observe(this,{
            if (it.ingredients != null) {
                viewDialog.title_ingredient_name.text = it.ingredients[0].strIngredient
                viewDialog.text_ingredient_type.text = "Type: ${it.ingredients[0].strType}"
                viewDialog.text_ingredient_alco.text = "Alcohol: ${it.ingredients[0].strAlcohol}"
                viewDialog.text_ingredient_abv.text = "ABV: ${it.ingredients[0].strABV}"
                viewDialog.text_desc.text = it.ingredients[0].strDescription
                viewDialog.button_see_drinks.text = "Drinks with ${it.ingredients[0].strIngredient}"
                Picasso.with(viewDialog.context)
                    .load(NetworkService.COCKTAIL_INGREDIENTS_URL + it.ingredients[0].strIngredient + ".png")
                    .into(viewDialog.image_ingredient_desc)
                if (it.ingredients[0].strDescription == null){
                    viewDialog.textView5.visibility = LinearLayout.GONE
                    return@observe
                }
                viewDialog.textView5.visibility = LinearLayout.VISIBLE
            }
        })
        viewDialog.button_see_drinks.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("filter", FilterEntity(ingredients = ingredientName))
            this.findNavController().navigate(R.id.action_ingredientsDescBSFragment_to_allCocktailsFragment,bundle)
        }
        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CocktailInfoViewModel::class.java)
    }

}

