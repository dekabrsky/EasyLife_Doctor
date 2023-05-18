package ru.dekabrsky.dialings.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.ItemDialingBinding
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.dialings.presentation.model.ConcurentUiModel
import kotlin.math.roundToInt

class ConcurentsListAdapter() : RecyclerView.Adapter<ConcurentsListAdapter.DialingHolder>() {

    private var items: MutableList<ConcurentUiModel> = arrayListOf()

    fun updateItems(newItems: List<ConcurentUiModel>) {
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
            scenarioValue.visibility = View.GONE
            scenarioIcon.visibility = View.GONE
            avgPrice.text = "${item.price.roundToInt()} ₽"
            minMaxPrices.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    class DialingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemDialingBinding.bind(itemView)
    }
}