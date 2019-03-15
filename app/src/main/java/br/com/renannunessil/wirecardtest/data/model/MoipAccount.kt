package br.com.renannunessil.wirecardtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoipAccount (
    @SerializedName("id")
    var id: String
): Parcelable