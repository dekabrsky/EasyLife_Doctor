package ru.dekabrsky.common.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import main.utils.visible
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.easylife.uikit.databinding.ClickableTitleSubtitleItemBinding
import ru.dekabrsky.events.common.R

class MiniDialingsAdapter(
    private val onItemClick: (TakingMedicationsUiModel) -> Unit
): RecyclerView.Adapter<MiniDialingsAdapter.CallersBaseDialingHolder>() {

    private var items: MutableList<TakingMedicationsUiModel> = arrayListOf()

    fun updateItems(newItems: List<TakingMedicationsUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CallersBaseDialingHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.clickable_title_subtitle_item, parent, false)

        return CallersBaseDialingHolder(view)
    }

    override fun onBindViewHolder(holder: CallersBaseDialingHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.subtitle.text = item.dateTime // todo
        holder.root.setOnClickListener { onItemClick(item) }
        item.isMedicationTaken?.let {
            if (it) {
                holder.icon.setImageResource(R.drawable.ic_done)
            } else {
                holder.icon.setImageResource(R.drawable.ic_cancel)
            }
        }
        item.isGameDone?.let {
            if (it && item.isMedicationTaken == true) {
                holder.detail.visible()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class CallersBaseDialingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ClickableTitleSubtitleItemBinding.bind(itemView)
        val root = binding.root
        val title = binding.title
        val subtitle = binding.subtitle
        val icon = binding.icon
        val detail = binding.detail
    }
}