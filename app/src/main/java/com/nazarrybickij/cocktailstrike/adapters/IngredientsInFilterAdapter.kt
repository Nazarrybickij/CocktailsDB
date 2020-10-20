package com.nazarrybickij.cocktailstrike.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.cocktailstrike.R
import kotlinx.android.synthetic.main.item_ingredient.view.ingredient_name_in_item
import kotlinx.android.synthetic.main.item_ingredients_filter.view.*
import java.util.*
import kotlin.collections.ArrayList


class IngredientsInFilterAdapter(var values: ArrayList<String>) :
    RecyclerView.Adapter<IngredientsInFilterAdapter.ViewHolder>(), Filterable {
    private var filteredList = ArrayList<String>()
    var selectedList = ArrayList<String>()
    lateinit var callback: IngredientsInFilterAdapter.AdapterCallback

    init {
        filteredList = values
    }

    interface AdapterCallback {
        fun onIngredientClick(ingredient: String)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)

    }

    override fun getItemCount(): Int = filteredList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredients_filter, parent, false)

        return ViewHolder(
            itemView
        )
    }

    fun updateSelectedList(ingredient: String) {
        selectedList.remove(ingredient)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun onBind(position: Int) {
            var name = filteredList[position]
            itemView.ingredient_name_in_item.text = name
                if (selectedList.contains(name)) {
                    itemView.checkbox_view.isChecked = true
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                    return
                }
            itemView.checkbox_view.isChecked = false
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grey))

        }

        @Suppress("SENSELESS_COMPARISON")
        override fun onClick(v: View?) {
            if (v != null) {
                try {
                    val checkBox = v.findViewById<CheckBox>(R.id.checkbox_view)
                    if (checkBox.isChecked) {
                        checkBox.isChecked = false
                        selectedList.remove(v.findViewById<TextView>(R.id.ingredient_name_in_item).text.toString())
                        v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.grey))
                    } else {
                        checkBox.isChecked = true
                        selectedList.add(v.findViewById<TextView>(R.id.ingredient_name_in_item).text.toString())
                        v.setBackgroundColor(ContextCompat.getColor(v.context, R.color.white))
                    }
                    if (callback != null) {
                        callback.onIngredientClick(filteredList.get(layoutPosition))
                    }
                } catch (e: Exception) {
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
                    val resultList = ArrayList<String>()
                    for (row in values) {
                        if (row.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
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
                filteredList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }
}