package ru.dekabrsky.callersbase.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.ItemChatMessageBinding
import ru.dekabrsky.callersbase.presentation.model.ChatMessageUiModel
import kotlin.contracts.contract

class ChatMessagesAdapter: RecyclerView.Adapter<ChatMessagesAdapter.MessageHolder>() {

    private var items: MutableList<ChatMessageUiModel> = arrayListOf()

    fun updateItems(newItems: List<ChatMessageUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addMessage(msg: ChatMessageUiModel) {
        items.add(0, msg)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)

        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val item = items[position]
        with (holder.binding) {
            dateTime.text = item.dateTime
            message.text = item.message
            if (item.isMyMessage) {
                message.backgroundTintList = ColorStateList.valueOf(holder.context.getColor(R.color.cyan500))
                userName.text = "Я"
            } else {
                message.backgroundTintList = ColorStateList.valueOf(holder.context.getColor(R.color.grey_100))
                userName.text = item.userName
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemChatMessageBinding.bind(itemView)
        val context = itemView.context
    }
}