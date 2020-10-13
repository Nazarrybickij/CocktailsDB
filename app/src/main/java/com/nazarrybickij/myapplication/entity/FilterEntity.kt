package com.nazarrybickij.myapplication.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterEntity(
    var alco:String = "",
    var category:String = "",
    var ingredients:String = "",
    var glass:String = ""
):Parcelable