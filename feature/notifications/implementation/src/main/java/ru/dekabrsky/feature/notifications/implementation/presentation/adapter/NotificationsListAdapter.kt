package ru.dekabrsky.feature.notifications.implementation.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.ItemNotificationBinding
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity

class NotificationsListAdapter(
    private val onItemClick: (NotificationEntity) -> Unit
): RecyclerView.Adapter<NotificationsListAdapter.NotificationHolder>() {

    private var items: MutableList<NotificationEntity> = arrayListOf()

    fun updateItems(newItems: List<NotificationEntity>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)

        return NotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val item = items[position]
        with (holder.binding) {
            tabletName.text = item.tabletName
            dosage.text = item.dosage
            note.text = item.note
            notificationTime.text = mapTime(item.hour, item.minute)
            notificationSwitch.isChecked = true
            notificationSwitch.isEnabled = false
            root.setOnClickListener { onItemClick(item) }
        }
    }

    private fun mapTime(hour: Int, minute: Int): String {
        val hour = if (hour < MIN_TWO_DIGIT_MINUTE) "0$hour" else hour.toString()
        val minute = if (minute < MIN_TWO_DIGIT_MINUTE) "0$minute" else minute.toString()
        return "$hour:$minute"
    }

    override fun getItemCount(): Int = items.size

    class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemNotificationBinding.bind(itemView)
    }

    companion object {
        private const val MIN_TWO_DIGIT_MINUTE = 10
    }
}