package ru.dekabrsky.scenarios.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.ItemScenarioBinding
import ru.dekabrsky.scenarios_common.presentation.model.ScenarioItemUiModel

class ScenariosAdapter(
    private val onItemClick: (ScenarioItemUiModel) -> Unit
): RecyclerView.Adapter<ScenariosAdapter.ScenarioHolder>() {

    private var items: MutableList<ScenarioItemUiModel> = arrayListOf()

    fun updateItems(newItems: List<ScenarioItemUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScenarioHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scenario, parent, false)

        return ScenarioHolder(view)
    }

    override fun onBindViewHolder(holder: ScenarioHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.date.text = item.date
        holder.stepsCount.text = item.stepsCount
        holder.root.setOnClickListener { onItemClick(item) }
        holder.title.setOnClickListener { onItemClick(item) }
        if (item.hasWarning) {
            holder.warningIcon.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = items.size

    class ScenarioHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemScenarioBinding.bind(itemView)
        val title = binding.scenarioName
        val date = binding.creationDate
        //val nextCallInfo = binding.nextCallChip
        val warningIcon = binding.warningIcon
        val stepsCount = binding.stepsCount
        val root = binding.root
    }
}