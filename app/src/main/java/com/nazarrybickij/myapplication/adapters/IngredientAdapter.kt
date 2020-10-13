package com.nazarrybickij.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.myapplication.R
import com.nazarrybickij.myapplication.entity.IngredientEntity
import com.nazarrybickij.myapplication.network.NetworkService.Companion.COCKTAIL_INGREDIENTS_URL
import com.nazarrybickij.myapplication.network.NetworkService.Companion.COCKTAIL_INGREDIENT_PNG_SMALL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientAdapter(
    private val values: MutableList<IngredientEntity>
//    ,
//    private val callback: CocktailsAdapter.AdapterCallback?
) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    interface AdapterCallback {
        fun onGroupClick()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)

        return ViewHolder(
            itemView
        )
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//        ,
//        View.OnClickListener
    {
        //        init {
//            itemView.setOnClickListener(this)
//        }
        fun onBind(position: Int) {
            var name = values[position].name
            itemView.ingredient_name_in_item.text = name
            itemView.measure_in_item.text = values[position].measure
            var nameforimage = name.replace(" ", "%20")
            Picasso.with(itemView.context)
                .load(COCKTAIL_INGREDIENTS_URL+ nameforimage + COCKTAIL_INGREDIENT_PNG_SMALL)
                .error(R.drawable.error_ingredient_image)
                .into(itemView.image_ingredient)

        }

//        override fun onClick(v: View?) {
//            callback?.onGroupClick(
//
//            )
//        }

    }
}