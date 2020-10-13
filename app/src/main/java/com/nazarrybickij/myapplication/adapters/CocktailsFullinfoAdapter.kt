package com.nazarrybickij.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.myapplication.R
import com.nazarrybickij.myapplication.entity.DrinkX
import com.nazarrybickij.myapplication.viewmodels.CocktailInfoViewModel.Companion.convertDrinkInfoInIngredientList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cocktails.view.name_cocktail
import kotlinx.android.synthetic.main.item_popular_list.view.*

class CocktailsFullinfoAdapter(
    private val values: List<DrinkX>,
    private val callback: AdapterCallback?
) : RecyclerView.Adapter<CocktailsFullinfoAdapter.ViewHolder>() {

    interface AdapterCallback {
        fun onCocktailClick(id:String)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_list, parent, false)

        return ViewHolder(
            itemView
        )
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun onBind(position: Int) {
            Picasso.with(itemView.context).load(values[position].strDrinkThumb).into(itemView.image_cocktail_in_popular)
            itemView.name_cocktail.text = values[position].strDrink
            itemView.alco_under_title_view.text = values[position].strAlcoholic
            itemView.category_under_title_view.text = values[position].strCategory
            var listIngredientsEntity = convertDrinkInfoInIngredientList(values[position])
            var listIngredients = mutableListOf<String>()
            for (i in listIngredientsEntity){
                listIngredients.add(i.name)
            }
            itemView.ingredient_list_text_view.text = listIngredients.joinToString()
        }

        override fun onClick(v: View?) {
            callback?.onCocktailClick(values[layoutPosition].idDrink)
        }

    }
}