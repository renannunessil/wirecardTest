package br.com.renannunessil.wirecardtest.repository

import br.com.renannunessil.wirecardtest.data.model.Order
import br.com.renannunessil.wirecardtest.data.model.OrdersResponse
import br.com.renannunessil.wirecardtest.data.remote.CallApi
import br.com.renannunessil.wirecardtest.data.remote.RetrofitClientInstance
import br.com.renannunessil.wirecardtest.utils.Constants
import retrofit2.Call

class OrdersRepository {
    private val service: CallApi = RetrofitClientInstance.create(Constants.ORDERS_URL)

    fun getOrders(token: String): Call<OrdersResponse> {
        return service.getOrders(token)
    }

    fun getOrder(token: String, orderId: String): Call<Order> {
        return service.getOrder(token, orderId)
    }

    object OrdersRepositoryProvider {
        fun provideOrdersRepository(): OrdersRepository {
            return OrdersRepository()
        }
    }
}