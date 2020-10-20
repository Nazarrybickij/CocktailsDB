package com.nazarrybickij.cocktailstrike.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.entity.CategoriesEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_categories_drink.view.*


class CategoriesDrinkAdapter(
) : RecyclerView.Adapter<CategoriesDrinkAdapter.ViewHolder>() {
    var values = mutableListOf<CategoriesEntity>()
    var callback: AdapterCallback? = null
    interface AdapterCallback {
        fun onCategoriesClick(category:String)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_categories_drink, parent, false)

        return ViewHolder(itemView)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun onBind(position: Int) {
            itemView.name_categories.text = values[position].strCategory
            Picasso.with(itemView.context).load(values[position].strImage).fit().centerCrop()
                .into(itemView.categories_image)

        }

        override fun onClick(v: View?) {
            if (callback != null){
                callback?.onCategoriesClick(values[layoutPosition].strCategory)
            }
        }

    }
}