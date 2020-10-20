package com.nazarrybickij.cocktailstrike.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cocktails.view.*



class CocktailsAdapterForSearch() : RecyclerView.Adapter<CocktailsAdapterForSearch.ViewHolder>() {
    private var values= mutableListOf<DrinkX>()
    lateinit var  callback: AdapterCallback

    interface AdapterCallback {
        fun onCocktailClick(id: String)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cocktails_for_search, parent, false)

        return ViewHolder(
            itemView
        )
    }
    fun inputValue(drinks:MutableList<DrinkX>){
        values = drinks
        notifyDataSetChanged()
    }
    fun clearValue(){
        values.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun onBind(position: Int) {
            itemView.name_cocktail.text = values[position].strDrink
            Picasso.with(itemView.context).load(values[position].strDrinkThumb)
                .into(itemView.image_cocktail)
        }

        @Suppress("SENSELESS_COMPARISON")
        override fun onClick(v: View?) {
            if (callback != null){
                callback.onCocktailClick(values[layoutPosition].idDrink)
            }
        }

    }


}