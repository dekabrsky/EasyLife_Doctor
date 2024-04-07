package ru.dekabrsky.callersbase.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import main.utils.visible
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.ItemChatBinding
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel

class ChatsListAdapter(
    private val onItemClick: (ChatUiModel) -> Unit
): RecyclerView.Adapter<ChatsListAdapter.ChatHolder>() {

    private var items: MutableList<ChatUiModel> = arrayListOf()

    init {
        setHasStableIds(true)
    }

    fun updateItems(newItems: List<ChatUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = items[position].chatId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)

        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemChatBinding.bind(itemView)

        fun bind(item: ChatUiModel) {
            binding.name.text = item.secondUser.nameWithNickname
            binding.avatarPlaceholder.text = item.avatarPlaceholder
            binding.lastMessageTime.text = item.date
            binding.lastMessage.text = item.lastMessage

            if (item.newMessagesCount > 0) {
                binding.newMessagesCount.visible()
                binding.newMessagesCount.text = item.newMessagesCount.toString()
                binding.lastMessage.setTextAppearance(R.style.BoldText)
                binding.lastMessage.setTextColor(itemView.context.getColor(R.color.grey_600))
            }

            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}