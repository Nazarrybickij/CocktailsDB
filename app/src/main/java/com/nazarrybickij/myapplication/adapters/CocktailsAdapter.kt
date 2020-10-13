package com.nazarrybickij.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.myapplication.R
import com.nazarrybickij.myapplication.entity.Drink
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun onBind(position: Int) {
            itemView.name_cocktail.text = filteredList[position].strDrink
            Picasso.with(itemView.context).load(filteredList[position].strDrinkThumb)
                .into(itemView.image_cocktail)
        }

        override fun onClick(v: View?) {
            if (callback != null){
                callback.onCocktailClick(filteredList[layoutPosition].idDrink)
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