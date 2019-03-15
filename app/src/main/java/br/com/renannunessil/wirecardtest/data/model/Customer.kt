package br.com.renannunessil.wirecardtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customer(
    @SerializedName("email")
    var email: String,
    @SerializedName("fullname")
    var customerName: String
): Parcelable