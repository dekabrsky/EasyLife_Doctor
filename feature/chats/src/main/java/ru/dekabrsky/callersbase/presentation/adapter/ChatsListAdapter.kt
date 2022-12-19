package ru.dekabrsky.callersbase.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.ItemChatBinding
import ru.dekabrsky.callersbase_common.presentation.model.ChatUiModel

class ChatsListAdapter(
    private val onItemClick: (ChatUiModel) -> Unit
): RecyclerView.Adapter<ChatsListAdapter.ChatHolder>() {

    private var items: MutableList<ChatUiModel> = arrayListOf()

    fun updateItems(newItems: List<ChatUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)

        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.date.text = item.date
        holder.message.text = item.lastMessage
        if (item.newMessagesCount > 0) {
            holder.count.visibility = View.VISIBLE
            holder.count.text = item.newMessagesCount.toString()
            holder.message.setTextAppearance(R.style.BoldText)
            holder.message.setTextColor(holder.itemView.context.getColor(R.color.grey_600))
        }
        if (item.name.contains("373")) {
            holder.avatar.setImageResource(R.drawable.cat_avatar)
        }
        if (item.name.contains("0356")) {
            holder.avatar.setImageResource(R.drawable.cat_avatar_2)
        }
        if (item.name.contains("Вас")) {
            holder.avatar.setImageResource(R.drawable.ic_doctor_m)
        }
        if (item.name.contains("Нат")) {
            holder.avatar.setImageResource(R.drawable.ic_doctor_f)
        }
        holder.root.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount(): Int = items.size

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemChatBinding.bind(itemView)
        val root = binding.root
        val name = binding.name
        val date = binding.lastMessageTime
        val count = binding.newMessagesCount
        val message = binding.lastMessage
        val avatar = binding.avatar
    }
}