package com.nazarrybickij.cocktailstrike.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cocktails.view.*
import java.util.*
import kotlin.collections.ArrayList


class CocktailsAdapter() : RecyclerView.Adapter<CocktailsAdapter.ViewHolder>(), Filterable {
    private var filteredList = mutableListOf<Drink>()
    private var values= mutableListOf<Drink>()
    lateinit var  callback: AdapterCallback

    interface AdapterCallback {
        fun onCocktailClick(id: String)
        fun onFavClick(drink: Drink)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount() = filteredList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cocktails, parent, false)

        return ViewHolder(
            itemView
        )
    }
    fun inputValue(drinks:MutableList<Drink>){
        values = drinks
        filteredList = values
        notifyDataSetChanged()
    }
    fun getValues() = filteredList

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.fav_in_item.setOnClickListener(this)
        }

        fun onBind(position: Int) {
            if (DBRepository.getInstance().isContain(filteredList[position].idDrink.toInt())){
                itemView.fav_in_item.setImageResource(R.drawable.star_selected_icon)
            }else{
                itemView.fav_in_item.setImageResource(R.drawable.star_icon)
            }

            itemView.name_cocktail.text = filteredList[position].strDrink
            Picasso.with(itemView.context).load(filteredList[position].strDrinkThumb)
                .into(itemView.image_cocktail)
        }

        @Suppress("SENSELESS_COMPARISON")
        override fun onClick(v: View?) {
            if (callback != null){
            when(v){
                itemView.fav_in_item -> callback.onFavClick(filteredList[layoutPosition])
                itemView -> callback.onCocktailClick(filteredList[layoutPosition].idDrink)
            }

            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = values
                } else {
                    val resultList = ArrayList<Drink>()
                    for (row in values) {
                        if (row.strDrink.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<Drink>
                notifyDataSetChanged()
            }

        }
    }

}