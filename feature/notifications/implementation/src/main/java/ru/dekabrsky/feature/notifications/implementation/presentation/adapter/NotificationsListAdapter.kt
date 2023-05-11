package ru.dekabrsky.feature.notifications.implementation.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.ItemNotificationBinding
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationItemUiModel

class NotificationsListAdapter: RecyclerView.Adapter<NotificationsListAdapter.NotificationItemHolder>() {

    private var items: MutableList<NotificationItemUiModel> = arrayListOf()

    fun updateItems(newItems: List<NotificationItemUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)

        return NotificationItemHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationItemHolder, position: Int) {
        val item = items[position]
        holder.time.text = item.time
        holder.name.text = item.name
        holder.dosage.text = item.dosage
        holder.note.text = item.note
        holder.switch.isChecked = item.enabled
    }

    override fun getItemCount(): Int = items.size

    class NotificationItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNotificationBinding.bind(itemView)
        val time = binding.notificationTime
        val name = binding.tabletName
        val dosage = binding.dosage
        val note = binding.note
        val switch = binding.notificationSwitch
    }
}