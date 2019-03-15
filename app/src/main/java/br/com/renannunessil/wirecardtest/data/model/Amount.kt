package br.com.renannunessil.wirecardtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Amount(
    @SerializedName("total")
    var totalAmount: Double,
    @SerializedName("fees")
    var fees: Double,
    @SerializedName("liquid")
    var liquidAmount: Double
): Parcelable