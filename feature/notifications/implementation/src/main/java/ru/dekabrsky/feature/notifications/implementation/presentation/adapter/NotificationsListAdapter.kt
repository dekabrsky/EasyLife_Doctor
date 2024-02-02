package ru.dekabrsky.feature.notifications.implementation.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import main.utils.orZero
import main.utils.setIsCheckedWithoutEffects
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationMedicineEntity
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.databinding.ItemNotificationBinding

class NotificationsListAdapter(
    private val onItemClick: (NotificationEntity) -> Unit,
    private val onItemDelete: (NotificationEntity) -> Unit,
    private val onItemCheckedChanged: (NotificationEntity, Boolean) -> Unit,
    private val formatDosage: (NotificationMedicineEntity) -> String
): RecyclerView.Adapter<NotificationsListAdapter.NotificationHolder>() {

    private var items: MutableList<NotificationEntity> = arrayListOf()

    private val viewBinderHelper = ViewBinderHelper()

    fun updateItems(newItems: List<NotificationEntity>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)

        return NotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val item = items[position]

        holder.bind(item)
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.binding.swipeReveal, item.uid.toString())
        viewBinderHelper.closeLayout(item.uid.toString())
    }

    private fun mapTime(hour: Int, minute: Int): String {
        val hour = if (hour < MIN_TWO_DIGIT_MINUTE) "0$hour" else hour.toString()
        val minute = if (minute < MIN_TWO_DIGIT_MINUTE) "0$minute" else minute.toString()
        return "$hour:$minute"
    }

    override fun getItemCount(): Int = items.size

    inner class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemNotificationBinding.bind(itemView)

        fun bind(item: NotificationEntity) {
            val firstMedicine = item.medicines.firstOrNull()

            with (binding) {
                tabletName.text = item.medicines.joinToString { it.name }
                firstMedicine?.let {
                    dosage.text = formatDosage(it).takeIf { item.medicines.size == 1 }.orEmpty()
                    note.text = it.note.takeIf { item.medicines.size == 1 }.orEmpty()
                }
                notificationTime.text = mapTime(item.hour, item.minute)
                notificationSwitch.setIsCheckedWithoutEffects(item.enabled) { isChecked ->
                    onItemCheckedChanged.invoke(item, isChecked)
                }
                deleteWrapper.setOnClickListener { onItemDelete(item) }
                content.setOnClickListener { onItemClick(item) }
            }
        }
    }

    companion object {
        private const val MIN_TWO_DIGIT_MINUTE = 10
    }
}