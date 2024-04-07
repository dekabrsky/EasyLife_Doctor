package ru.dekabrsky.scenarios.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import main.utils.setBoolVisibility
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.ItemExistingParentBinding

class SelectParentAdapter (
    private val onItemClick: (ContactEntity) -> Unit,
    private val isChecked: (Long) -> Boolean
): RecyclerView.Adapter<SelectParentAdapter.SelectParentHolder>() {

    private var items: MutableList<ContactEntity> = arrayListOf()

    fun updateItems(newItems: List<ContactEntity>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectParentHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_existing_parent, parent, false)

        return SelectParentHolder(view)
    }

    override fun onBindViewHolder(holder: SelectParentHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.displayName
        holder.nickname.text = item.nickName
        holder.root.setOnClickListener { onItemClick(item) }
        holder.check.setBoolVisibility(isChecked.invoke(item.id))
    }

    override fun getItemCount(): Int = items.size

    class SelectParentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemExistingParentBinding.bind(itemView)
        val name = binding.existingParent
        val nickname = binding.existingParentNickname
        val check = binding.checkIcon
        val root = binding.root
    }
}