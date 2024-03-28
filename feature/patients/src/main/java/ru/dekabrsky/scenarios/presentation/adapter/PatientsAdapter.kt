package ru.dekabrsky.scenarios.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import main.utils.setBoolVisibility
import main.utils.visible
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.ItemExistingParentBinding
import ru.dekabrsky.scenarios.databinding.ItemScenarioBinding

class PatientsAdapter(
    private val onItemClick: (ContactEntity) -> Unit
): RecyclerView.Adapter<PatientsAdapter.ScenarioHolder>() {

    private var items: MutableList<ContactEntity> = arrayListOf()

    fun updateItems(newItems: List<ContactEntity>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScenarioHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_existing_parent, parent, false)

        return ScenarioHolder(view)
    }

    override fun onBindViewHolder(holder: ScenarioHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ScenarioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemExistingParentBinding.bind(itemView)

        fun bind(patient: ContactEntity) {
            binding.existingParent.text = patient.name
            binding.checkIcon.setBoolVisibility(false)
            binding.root.setOnClickListener { onItemClick.invoke(patient) }
        }
    }
}