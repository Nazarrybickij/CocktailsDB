package com.nazarrybickij.cocktailstrike.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.nazarrybickij.cocktailstrike.App
import com.nazarrybickij.cocktailstrike.R
import com.nazarrybickij.cocktailstrike.db.DBRepository
import com.nazarrybickij.cocktailstrike.entity.Drink
import com.nazarrybickij.cocktailstrike.entity.DrinkX
import com.nazarrybickij.cocktailstrike.viewmodels.CocktailInfoViewModel.Companion.convertDrinkInfoInIngredientList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cocktails.view.name_cocktail
import kotlinx.android.synthetic.main.item_popular_list.view.*
import kotlinx.android.synthetic.main.item_popular_list.view.fav_in_item

class CocktailsFullinfoAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var values = mutableListOf<DrinkX>()
    var callback: AdapterCallback? = null
    val AD_TYPE = 1
    val CONTENT_TYPE = 0
    lateinit var context: Context
    interface AdapterCallback {
        fun onCocktailClick(id:String)
        fun onFavClick(drink: Drink)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CONTENT_TYPE) {
            (holder as CocktailsFullinfoAdapter.ViewHolder).onBind(position)
        }

    }

    override fun getItemCount() = values.size
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
            .inflate(R.layout.item_popular_list, parent, false)
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
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
            itemView.fav_in_item.setOnClickListener(this)
        }
        fun onBind(position: Int) {
            if (DBRepository.getInstance().isContain(values[position].idDrink.toInt())){
                itemView.fav_in_item.setImageResource(R.drawable.star_selected_icon)
            }else{
                itemView.fav_in_item.setImageResource(R.drawable.star_icon)
            }
            Picasso.with(itemView.context).load(values[position].strDrinkThumb).into(itemView.image_cocktail_in_popular)
            itemView.name_cocktail.text = values[position].strDrink
            itemView.alco_under_title_view.text = values[position].strAlcoholic
            itemView.category_under_title_view.text = values[position].strCategory
            val listIngredientsEntity = convertDrinkInfoInIngredientList(values[position])
            var listIngredients = mutableListOf<String>()
            for (i in listIngredientsEntity){
                listIngredients.add(i.name)
            }
            itemView.ingredient_list_text_view.text = listIngredients.joinToString()
        }
        override fun onClick(v: View?) {
            if (callback != null){
                when(v){
                    itemView.fav_in_item -> callback!!.onFavClick(convertDrinkXToDrink(values[layoutPosition]))
                    itemView -> callback!!.onCocktailClick(values[layoutPosition].idDrink)
                }

            }
        }
    }
    fun convertDrinkXToDrink(drinkX: DrinkX): Drink {
        return Drink(drinkX.idDrink,drinkX.strDrink,drinkX.strDrinkThumb)
    }
}