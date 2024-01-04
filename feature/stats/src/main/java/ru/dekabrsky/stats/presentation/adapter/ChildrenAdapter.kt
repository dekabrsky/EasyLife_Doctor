package ru.dekabrsky.stats.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import main.utils.visible
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.stats.R
import ru.dekabrsky.stats.databinding.IncludeStatsLineChartBinding
import ru.dekabrsky.stats.databinding.IncludeTitleValueBinding

class ChildrenAdapter(
    private val onItemClick: (ContactEntity) -> Unit = {}
): RecyclerView.Adapter<ChildrenAdapter.ChildHolder>() {

    private var items: MutableList<ContactEntity> = arrayListOf()

    fun updateItems(newItems: List<ContactEntity>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.include_title_value, parent, false)

        return ChildHolder(view)
    }

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val item = items[position]
        holder.title.setText(R.string.myChild)
        holder.value.text = item.name
        holder.root.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = IncludeTitleValueBinding.bind(itemView)
        val root = binding.root
        val title = binding.title
        val value = binding.value
    }
}