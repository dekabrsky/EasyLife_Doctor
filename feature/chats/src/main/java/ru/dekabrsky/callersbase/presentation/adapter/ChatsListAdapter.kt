package ru.dekabrsky.callersbase.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.ItemCallersBaseBinding
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel

class ChatsListAdapter(
    private val onItemClick: (CallersBaseUiModel) -> Unit
): RecyclerView.Adapter<ChatsListAdapter.CallersBaseHolder>() {

    private var items: MutableList<CallersBaseUiModel> = arrayListOf()

    fun updateItems(newItems: List<CallersBaseUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallersBaseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_callers_base, parent, false)

        return CallersBaseHolder(view)
    }

    override fun onBindViewHolder(holder: CallersBaseHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.date.text = item.date
        holder.count.text =
            holder.itemView.context.resources.getString(R.string.count_format, item.count)
        val variablesAdapter = ChatsVariablesListAdapter()
        holder.variablesList.adapter = variablesAdapter
        variablesAdapter.updateItems(item.variables)
        holder.root.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    class CallersBaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCallersBaseBinding.bind(itemView)
        val root = binding.root
        val title = binding.baseName
        val date = binding.dateAndCount
        val count = binding.countValue
        val variablesList = binding.baseVariablesList
    }
}