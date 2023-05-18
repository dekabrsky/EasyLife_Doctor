package ru.dekabrsky.dialings.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.ItemDialingBinding
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.dialings.domain.model.PlainProduct
import kotlin.math.roundToInt

class DialingListAdapter(
    private val onItemClick: (PlainProduct) -> Unit,
    private val onRunClick: (Int) -> Unit
) : RecyclerView.Adapter<DialingListAdapter.DialingHolder>() {

    private var items: MutableList<PlainProduct> = arrayListOf()

    fun updateItems(newItems: List<PlainProduct>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialing, parent, false)

        return DialingHolder(view)
    }

    override fun onBindViewHolder(holder: DialingHolder, position: Int) {
        with(holder.binding) {
            val item = items[position]
            dialingTitle.text = item.name
            scenarioValue.text = item.city
            avgPrice.text = "${item.avg.roundToInt()} ₽"
            minMaxPrices.text = "${item.min.roundToInt()} ₽ - ${item.max.roundToInt()} ₽"
            root.setOnClickListener { onItemClick(item) }
        }
    }

    override fun getItemCount(): Int = items.size

    class DialingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemDialingBinding.bind(itemView)
    }
}