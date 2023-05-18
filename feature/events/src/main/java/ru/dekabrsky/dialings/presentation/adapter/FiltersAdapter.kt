package ru.dekabrsky.dialings.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.common.presentation.model.ChatUiModel
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.databinding.FilterItemBinding

class FiltersAdapter(
    private val onItemCheckedChange: (String, Boolean) -> Unit,
    private val isChecked: (String) -> Boolean
) : RecyclerView.Adapter<FiltersAdapter.ChatHolder>() {

    private var items: MutableList<String> = arrayListOf()

    fun updateItems(newItems: List<String>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_item, parent, false)

        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val item = items[position]
        holder.checkbox.text = item
        holder.checkbox.isChecked = isChecked.invoke(item)
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onItemCheckedChange(item, isChecked)
        }
    }

    override fun getItemCount(): Int = items.size

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = FilterItemBinding.bind(itemView)
        val checkbox = binding.checkbox
    }
}