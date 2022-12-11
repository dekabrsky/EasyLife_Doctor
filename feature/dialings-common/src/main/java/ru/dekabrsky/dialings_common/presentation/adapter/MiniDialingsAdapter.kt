package ru.dekabrsky.dialings_common.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.dialings_common.R
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.uikit.databinding.ClickableTitleSubtitleItemBinding

class MiniDialingsAdapter(
    private val onItemClick: (MiniDialingUiModel) -> Unit
): RecyclerView.Adapter<MiniDialingsAdapter.CallersBaseDialingHolder>() {

    private var items: MutableList<MiniDialingUiModel> = arrayListOf()

    fun updateItems(newItems: List<MiniDialingUiModel>) {
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
    }

    override fun getItemCount(): Int = items.size

    class CallersBaseDialingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ClickableTitleSubtitleItemBinding.bind(itemView)
        val root = binding.root
        val title = binding.title
        val subtitle = binding.subtitle
    }
}