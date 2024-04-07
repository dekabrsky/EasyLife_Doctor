package ru.dekabrsky.stats.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.stats.R
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = IncludeTitleValueBinding.bind(itemView)
        fun bind(item: ContactEntity) {
            binding.title.setText(R.string.myChild)
            binding.value.text = item.displayName
            binding.speciality.text = itemView.context.getString(R.string.nickname_pattern, item.nickName)
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}