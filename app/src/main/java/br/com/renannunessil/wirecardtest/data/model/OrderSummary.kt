package br.com.renannunessil.wirecardtest.data.model

import com.google.gson.annotations.SerializedName

data class OrderSummary(
    @SerializedName("count")
    var ordersCount: Int,
    @SerializedName("amount")
    var ordersAmount: Int
)