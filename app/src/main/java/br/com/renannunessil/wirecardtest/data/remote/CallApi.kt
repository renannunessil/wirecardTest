package br.com.renannunessil.wirecardtest.data.remote

import br.com.renannunessil.wirecardtest.data.model.LoginResponse
import br.com.renannunessil.wirecardtest.data.model.Order
import br.com.renannunessil.wirecardtest.data.model.OrdersResponse
import retrofit2.Call
import retrofit2.http.*

interface CallApi {

    @FormUrlEncoded
        @POST("oauth/token")
        fun callLogin(@Field("client_id") clientId: String,
                      @Field("client_secret") clientSecret: String,
                      @Field("grant_type") grantType: String,
                      @Field("username") username: String,
                      @Field("password") password: String,
                      @Field("device_id") deviceId: String,
                      @Field("scope") scope: String): Call<LoginResponse>

    @GET("v2/orders")
    fun getOrders(@Header("authorization") accessToken: String): Call<OrdersResponse>

    @GET("v2/orders/{order_id}")
    fun getOrder(
        @Header("authorization") accessToken: String,
        @Path("order_id") orderId: String): Call<Order>
}