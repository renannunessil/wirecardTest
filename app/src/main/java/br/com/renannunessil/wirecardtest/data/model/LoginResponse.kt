package br.com.renannunessil.wirecardtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse (
    @SerializedName("accessToken")
    var token: String,
    @SerializedName("moipAccount")
    var account: MoipAccount): Parcelable