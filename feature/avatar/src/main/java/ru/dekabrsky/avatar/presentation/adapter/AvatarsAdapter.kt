package ru.dekabrsky.avatar.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import main.utils.setBoolVisibility
import ru.dekabrsky.avatar.R
import ru.dekabrsky.avatar.databinding.ItemAvatarListBinding
import ru.dekabrsky.avatar.domain.AvatarType


class AvatarsAdapter  (
    private val onItemClick: (AvatarType) -> Unit,
    private val isItemSelected: (AvatarType) -> Boolean,
    private val provideUri: (AvatarType) -> Uri
): RecyclerView.Adapter<AvatarsAdapter.AvatarHolder>() {

    private var items: MutableList<AvatarType> = arrayListOf()

    fun updateItems(newItems: List<AvatarType>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_avatar_list, parent, false)

        return AvatarHolder(view)
    }

    override fun onBindViewHolder(holder: AvatarHolder, position: Int) {
        val item = items[position]

        holder.image.setOnClickListener { onItemClick(item) }
        holder.check.setBoolVisibility(isItemSelected.invoke(item))

        Glide
            .with(holder.itemView)
            .load(provideUri.invoke(item))
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(AVATAR_ROUND_RADIUS)))
            .into(holder.image)
    }

    override fun getItemCount(): Int = items.size

    class AvatarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAvatarListBinding.bind(itemView)
        val check = binding.check
        val image = binding.avatar
    }

    companion object {
        private const val AVATAR_ROUND_RADIUS = 16
    }
}