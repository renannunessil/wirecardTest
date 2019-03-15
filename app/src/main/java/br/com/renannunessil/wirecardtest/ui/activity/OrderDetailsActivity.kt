package br.com.renannunessil.wirecardtest.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.concrete.canarinho.formatador.Formatador
import br.com.renannunessil.wirecardtest.R
import br.com.renannunessil.wirecardtest.adapter.OrdersRecyclerViewAdapter
import br.com.renannunessil.wirecardtest.data.model.Order
import br.com.renannunessil.wirecardtest.databinding.ActivityOrderDetailsBinding
import br.com.renannunessil.wirecardtest.utils.Constants
import br.com.renannunessil.wirecardtest.utils.DateUtils
import br.com.renannunessil.wirecardtest.viewmodel.OrdersViewModel
import kotlinx.android.synthetic.main.activity_order_details.*

class OrderDetailsActivity: AppCompatActivity() {

    private val TOKEN = "token"
    private val ORDER = "order"
    private lateinit var viewModel: OrdersViewModel
    private lateinit var order: Order
    private lateinit var binding: ActivityOrderDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details)
        viewModel = ViewModelProviders.of(this)[OrdersViewModel::class.java]
        subscribe()
        callGetOrder()
    }

    private fun subscribe() {
        viewModel.getOrderObservable().observe(this, Observer {
            showLoading(false)
            if (it != null) order = it
            setupUi(order)
        })
    }

    private fun callGetOrder() {
        showLoading(true)
        viewModel.getOrder(intent.getStringExtra(TOKEN),
            intent.getParcelableExtra<Order>(ORDER).id)
    }

    private fun setupUi(order: Order) {
        binding.order = order
        binding.tvCreatedDate.text = DateUtils.formatDate(order.createdDate)
        binding.tvStatusDate.text = DateUtils.formatDate(order.statusDate)

        val totalAmount = Formatador.VALOR_COM_SIMBOLO.formata(order.orderAmount.totalAmount.toString())
        binding.tvAmount.text = totalAmount
        binding.tvTotalAmount.text = totalAmount

        binding.tvPaymentStatus.text = order.paymentStatus

        val statusColor = OrdersRecyclerViewAdapter.statusColor(order)
        binding.tvPaymentStatus.setTextColor(ContextCompat.getColor(application, statusColor[0]))
        binding.tvPaymentStatus.background = application.getDrawable(statusColor[1])

        binding.tvFees.text = Formatador.VALOR_COM_SIMBOLO.formata(order.orderAmount.fees.toString())
        binding.tvLiquidAmount.text = Formatador.VALOR_COM_SIMBOLO.formata(order.orderAmount.liquidAmount.toString())

        val payments: String = "${order.payments.size} pagamento(s)"
        binding.tvTotalPayments.text = payments

        when(order.payments[0].fundingInstrument.paymentMethod) {
            Constants.CREDIT_CARD -> binding.tvPaymentMethod.text = applicationContext.getText(R.string.credit_card)
            else -> binding.tvPaymentMethod.text = applicationContext.getText(R.string.boleto)
        }
    }

    private fun showLoading(show: Boolean) {
        when(show) {
            true -> cl_order_details_progressbar.visibility = View.VISIBLE
            else -> cl_order_details_progressbar.visibility = View.GONE
        }
    }
}