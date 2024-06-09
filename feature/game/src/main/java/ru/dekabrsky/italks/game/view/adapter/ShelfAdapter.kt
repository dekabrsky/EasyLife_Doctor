package ru.dekabrsky.easylife.game.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.easylife.game.R
import ru.dekabrsky.easylife.game.databinding.ItemOnShelfBinding
import ru.dekabrsky.easylife.game.view.model.ShelfItemUiModel

class ShelfAdapter: RecyclerView.Adapter<ShelfAdapter.ShelfItemHolder>() {

    private var items: MutableList<ShelfItemUiModel> = arrayListOf()

    fun updateItems(newItems: List<ShelfItemUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShelfItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_on_shelf, parent, false)

        return ShelfItemHolder(view)
    }

    override fun onBindViewHolder(holder: ShelfItemHolder, position: Int) {
        val item = items[position]
        item.drawable?.let {
            holder.item.setImageResource(it)
        } ?: holder.item.setVisibility(View.INVISIBLE)
    }

    override fun getItemCount(): Int = items.size

    class ShelfItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOnShelfBinding.bind(itemView)
        val item = binding.item
    }
}