package ru.dekabrsky.materials.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.dekabrsky.materials.R
import ru.dekabrsky.materials.databinding.ItemMaterialBinding
import ru.dekabrsky.materials.presentation.model.MaterialDetailsUiModel

class MaterialsListAdapter (
    private val onItemClick: (MaterialDetailsUiModel) -> Unit
): RecyclerView.Adapter<MaterialsListAdapter.MaterialsHolder>() {

    private var items: MutableList<MaterialDetailsUiModel> = arrayListOf()

    fun updateItems(newItems: List<MaterialDetailsUiModel>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_material, parent, false)

        return MaterialsHolder(view)
    }

    override fun onBindViewHolder(holder: MaterialsHolder, position: Int) {
        val item = items[position]

        holder.root.setOnClickListener { onItemClick(item) }
        holder.name.text = item.title

        Glide
            .with(holder.itemView)
            .load(item.imageLink)
            .centerCrop()
            .into(holder.image)
    }

    override fun getItemCount(): Int = items.size

    class MaterialsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMaterialBinding.bind(itemView)
        val root = binding.root
        val name = binding.name
        val image = binding.avatar
    }
}