package br.com.renannunessil.wirecardtest.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import br.com.renannunessil.wirecardtest.data.model.Order
import br.com.renannunessil.wirecardtest.data.model.OrdersResponse
import br.com.renannunessil.wirecardtest.repository.OrdersRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersViewModel(application: Application): AndroidViewModel(application) {

    private val repository = OrdersRepository.OrdersRepositoryProvider.provideOrdersRepository()
    private val oauth: String = "OAuth "
    private lateinit var ordersResponse: MutableLiveData<OrdersResponse>
    private lateinit var getOrderResponse: MutableLiveData<Order>
    private lateinit var ordersResponseError: MutableLiveData<Unit>

    fun getOrdersResponseObservable(): LiveData<OrdersResponse> {
        if (!::ordersResponse.isInitialized) {
            ordersResponse = MutableLiveData()
        }

        return ordersResponse
    }

    fun getOrderObservable(): LiveData<Order> {
        if (!::getOrderResponse.isInitialized) {
            getOrderResponse = MutableLiveData()
        }

        return getOrderResponse
    }

    fun getOrdersErrorObservable(): LiveData<Unit> {
        if (!::ordersResponseError.isInitialized) {
            ordersResponseError = MutableLiveData()
        }

        return ordersResponseError
    }

    fun getOrders(token: String) {
        val apiToken = oauth + token
        val getOrders = repository.getOrders(apiToken)

        getOrders.enqueue(object : Callback<OrdersResponse>{
            override fun onFailure(call: Call<OrdersResponse>, throwable: Throwable) {
                Log.e("CallOrders", "Error: ${throwable.message}")
            }

            override fun onResponse(call: Call<OrdersResponse>, response: Response<OrdersResponse>) {
                if (response.isSuccessful) {
                    ordersResponse.value = response.body()
                } else {
                    ordersResponseError.postValue(Unit)
                }
            }

        })
    }

    fun getOrder(token: String, orderId: String) {
        val apiToken = oauth + token
        val getOrder = repository.getOrder(apiToken, orderId)

        getOrder.enqueue(object : Callback<Order>{
            override fun onFailure(call: Call<Order>, throwable: Throwable) {
                Log.e("CallOrders", "Error: ${throwable.message}")
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    getOrderResponse.value = response.body()
                }
            }

        })
    }
}