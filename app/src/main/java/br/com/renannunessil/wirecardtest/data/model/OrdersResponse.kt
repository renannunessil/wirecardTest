package br.com.renannunessil.wirecardtest.data.model

import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("summary")
    var summary: OrderSummary,
    @SerializedName("orders")
    var ordersList: List<Order>
)