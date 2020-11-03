package com.nazarrybickij.cocktailstrike

import android.util.Log
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.formats.UnifiedNativeAd
import java.util.*

class ControllerAds private constructor() {
    private lateinit var mInterstitialAd: InterstitialAd

    fun loadAd() {
        mInterstitialAd = InterstitialAd(App.context)
        mInterstitialAd.adUnitId = App.getResources.getString(R.string.interstitial_ads_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
    }

    fun getMInterstitialAd(): InterstitialAd {
        return mInterstitialAd
    }


    companion object {
        private var instance: ControllerAds? = null
        var count = 0
        var show = false
        val SPECEADS = 3
        fun getInstance(): ControllerAds {
            if (instance == null) {
                instance = ControllerAds()
            }

            return instance as ControllerAds
        }
    }
}