package br.com.renannunessil.wirecardtest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Order(
    @SerializedName("id")
    var id: String,
    @SerializedName("amount")
    var orderAmount: Amount,
    @SerializedName("customer")
    var customer: Customer,
    @SerializedName("ownId")
    var ownId: String,
    @SerializedName("status")
    var paymentStatus: String,
    @SerializedName("updatedAt")
    var statusDate: String,
    @SerializedName("createdAt")
    var createdDate: String,
    @SerializedName("payments")
    var payments: List<Payment>
):Parcelable