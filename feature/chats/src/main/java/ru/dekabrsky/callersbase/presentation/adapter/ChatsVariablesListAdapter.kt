package ru.dekabrsky.callersbase.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.ItemBaseVariableBinding

class ChatsVariablesListAdapter : RecyclerView.Adapter<ChatsVariablesListAdapter.CallersVariablesHolder>() {

    private var items: MutableList<String> = arrayListOf()

    fun updateItems(newItems: List<String>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallersVariablesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_base_variable, parent, false)

        return CallersVariablesHolder(view)
    }

    override fun onBindViewHolder(holder: CallersVariablesHolder, position: Int) {
        holder.variable.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    class CallersVariablesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemBaseVariableBinding.bind(itemView)
        val variable = binding.variable
    }
}