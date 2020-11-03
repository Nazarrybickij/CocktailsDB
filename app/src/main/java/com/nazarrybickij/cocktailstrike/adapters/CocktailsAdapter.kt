package com.nazarrybickij.cocktailstrike.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.nazarrybickij.cocktailstrike.App
import com.nazarrybickij.cocktailstrike.ControllerAds
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_cocktails.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask


class CocktailsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var filteredList = mutableListOf<Drink>()
    private var values = mutableListOf<Drink>()
    lateinit var callback: AdapterCallback
    val AD_TYPE = 1
    val CONTENT_TYPE = 0
    private lateinit var context: Context


    interface AdapterCallback {
        fun onCocktailClick(id: String)
        fun onFavClick(drink: Drink)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CONTENT_TYPE) {
            (holder as ViewHolder).onBind(position)
        }
    }

    override fun getItemCount() = filteredList.size
    override fun getItemViewType(position: Int): Int {
        val spaceBetweenAds = 6
        return if (position % (spaceBetweenAds + 1) == spaceBetweenAds) {
            AD_TYPE
        } else {
            CONTENT_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cocktails, parent, false)
        val itemAdsView = LayoutInflater.from(parent.context).inflate(
            R.layout.native_ad_in_list,
            parent,
            false
        )
        context = parent.context
        if (viewType == AD_TYPE) {
            return AdViewHolder(itemAdsView)
        } else {
            return ViewHolder(itemView)
        }

    }

    fun inputValue(drinks: MutableList<Drink>) {
        values = drinks
        filteredList = values
        notifyDataSetChanged()
    }

    fun getValues() = filteredList

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var nameCocktail: TextView = itemView.name_cocktail
        var imageCocktail: CircleImageView = itemView.image_cocktail

        init {
            itemView.setOnClickListener(this)
            itemView.fav_in_item.setOnClickListener(this)
        }

        fun onBind(position: Int) {
            if (DBRepository.getInstance().isContain(filteredList[position].idDrink.toInt())) {
                itemView.fav_in_item.setImageResource(R.drawable.star_selected_icon)
            } else {
                itemView.fav_in_item.setImageResource(R.drawable.star_icon)
            }

            nameCocktail.text = filteredList[position].strDrink
            Picasso.with(itemView.context).load(filteredList[position].strDrinkThumb)
                .error(R.drawable.no_image)
                .into(imageCocktail)
        }

        @Suppress("SENSELESS_COMPARISON")
        override fun onClick(v: View?) {
            if (callback != null) {
                when (v) {
                    itemView.fav_in_item -> callback.onFavClick(filteredList[layoutPosition])
                    itemView -> callback.onCocktailClick(filteredList[layoutPosition].idDrink)
                }

            }
        }

    }

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var adtemplate: TemplateView? = null
        init {
            adtemplate = itemView.findViewById(R.id.my_template)
            val adLoader: AdLoader =
                AdLoader.Builder(App.context, App.getResources.getString(R.string.native_ads_id))
                    .forUnifiedNativeAd { unifiedNativeAd ->
                        adtemplate!!.setStyles(NativeTemplateStyle.Builder().build())
                        adtemplate!!.setNativeAd(unifiedNativeAd)
                    }.build()
            adLoader.loadAd(AdRequest.Builder().build())
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
                        if (row.strDrink.toLowerCase(Locale.ROOT).contains(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
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
                filteredList = results?.values as MutableList<Drink>
                notifyDataSetChanged()
            }

        }
    }

}