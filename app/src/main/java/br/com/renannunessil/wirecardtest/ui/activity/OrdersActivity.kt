package br.com.renannunessil.wirecardtest.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.concrete.canarinho.formatador.Formatador
import br.com.renannunessil.wirecardtest.R
import br.com.renannunessil.wirecardtest.adapter.OrdersRecyclerViewAdapter
import br.com.renannunessil.wirecardtest.data.model.Order
import br.com.renannunessil.wirecardtest.data.model.OrdersResponse
import br.com.renannunessil.wirecardtest.data.model.User
import br.com.renannunessil.wirecardtest.viewmodel.OrdersViewModel
import br.com.renannunessil.wirecardtest.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity(), OrdersRecyclerViewAdapter.OrdersAdapterOnClickListener {

    private val TOKEN = "token"
    private val USER = "user"
    private lateinit var viewModel: OrdersViewModel
    private lateinit var userViewModel: UserViewModel
    private var ordersList: List<Order> = ArrayList()
    private lateinit var ordersResponse: OrdersResponse
    private lateinit var ordersAdapter: OrdersRecyclerViewAdapter
    private lateinit var token: String
    private val ORDER = "order"

    override fun onClick(order: Order) {
        var intent = Intent(this, OrderDetailsActivity::class.java)
        intent.putExtra(ORDER, order)
        intent.putExtra(TOKEN, token)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        viewModel = ViewModelProviders.of(this)[OrdersViewModel::class.java]
        userViewModel = ViewModelProviders.of(this)[UserViewModel::class.java]
        ordersAdapter = OrdersRecyclerViewAdapter(applicationContext, this)
        token = intent.getStringExtra(TOKEN)
        showLoading(true)
        subscribe()
        callOrders()
        saveUser()
    }

    private fun subscribe() {
        viewModel.getOrdersResponseObservable().observe(this, Observer {
            if (it != null) {
                showLoading(false)
                ordersResponse = it
                ordersList = it.ordersList
                setAdapter(ordersList)
                tv_total_orders.text = ordersList.size.toString()
                tv_total_orders_amount.text = Formatador.VALOR_COM_SIMBOLO.formata(totalAmount().toString())
            }
        })

        viewModel.getOrdersErrorObservable().observe(this, Observer {
            userViewModel.deleteUser(intent.getParcelableExtra(USER))
            finish()
        })
    }

    private fun setAdapter(ordersList: List<Order>) {
        val recyclerView: RecyclerView = rv_orders_list
        ordersAdapter.setData(ordersList)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = ordersAdapter
    }

    private fun callOrders() = viewModel.getOrders(token)

    private fun showLoading(show: Boolean) {
        when(show) {
            true -> cl_orders_progressbar.visibility = View.VISIBLE
            else -> cl_orders_progressbar.visibility = View.GONE
        }
    }

    private fun saveUser() {
        val user = intent.getParcelableExtra<User>(USER)
        userViewModel.saveUser(user)
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun totalAmount(): Double {
        var totalAmount: Double = 0.0
        ordersList.forEach {
            totalAmount += it.orderAmount.totalAmount
        }
        return totalAmount
    }
}