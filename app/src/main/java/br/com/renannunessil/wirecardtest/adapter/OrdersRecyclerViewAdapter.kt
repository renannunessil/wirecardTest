package br.com.renannunessil.wirecardtest.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.concrete.canarinho.formatador.Formatador
import br.com.renannunessil.wirecardtest.R
import br.com.renannunessil.wirecardtest.data.model.Order
import br.com.renannunessil.wirecardtest.databinding.ItemOrdersListBinding
import br.com.renannunessil.wirecardtest.utils.Constants
import br.com.renannunessil.wirecardtest.utils.DateUtils
import kotlin.collections.ArrayList


class OrdersRecyclerViewAdapter(val context: Context, val listener: OrdersAdapterOnClickListener):
    RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrdersViewHolder>() {

    private var ordersList: List<Order> = ArrayList()

    interface OrdersAdapterOnClickListener {
        fun onClick(order: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersRecyclerViewAdapter.OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrdersListBinding.inflate(inflater, parent, false)
        return OrdersViewHolder(binding)
    }

    override fun getItemCount(): Int = ordersList.size

    override fun onBindViewHolder(holder: OrdersRecyclerViewAdapter.OrdersViewHolder, position: Int)
            = holder.bind(ordersList[position])

    fun setData(orders: List<Order>) {
        this.ordersList = orders
        notifyDataSetChanged()
    }

    inner class OrdersViewHolder(private val binding: ItemOrdersListBinding):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: Order) {
            binding.tvTotalAmount.text = Formatador.VALOR_COM_SIMBOLO.formata(item.orderAmount.totalAmount.toString())
            binding.tvEmail.text = item.customer.email
            binding.tvOwnId.text = item.ownId
            binding.tvOrderDate.text = DateUtils.formatDate(item.statusDate)
            val paymentStatusTV = binding.tvPaymentStatus
            paymentStatusTV.text = item.paymentStatus
            val statusColors = statusColor(item)
            paymentStatusTV.setTextColor(ContextCompat.getColor(context, statusColors[0]))
            paymentStatusTV.background = context.getDrawable(statusColors[1])

            when(item.payments[0].fundingInstrument.paymentMethod){
                Constants.CREDIT_CARD -> binding.tvPaymentMethodIcon.text = context.getText(R.string.credit_card_icon)
                else -> binding.tvPaymentMethodIcon.text = context.getText(R.string.bar_code_icon)
            }
        }

        override fun onClick(v: View?) {
            listener.onClick(ordersList[adapterPosition])
        }

    }

    companion object {
        fun statusColor(item: Order): List<Int> {
            var statusColors = ArrayList<Int>()
            when(item.paymentStatus) {
                Constants.PAID -> {
                    statusColors.add(0, R.color.payed)
                    statusColors.add(1, R.drawable.payment_paid_status_border)
                    return statusColors
                }
                Constants.WAITING -> {
                    statusColors.add(0, R.color.waiting)
                    statusColors.add(1, R.drawable.payment_waiting_status_border)
                    return statusColors
                }
                else -> {
                    statusColors.add(0, R.color.not_payed)
                    statusColors.add(1, R.drawable.payment_not_paid_status_border)
                    return statusColors
                }
            }
        }
    }
}