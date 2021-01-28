package com.example.noci.lists.input

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.database_lists.items.Items
import com.example.noci.databinding.ItemStyleBinding

class ItemsAdapter(private val itemAdapterEdit: ItemsAdapterEdit, private val itemAdapterDelete: ItemsAdapterDelete) :
    ListAdapter<Items, ItemsAdapter.ViewHolder>(
        ShowNotesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        Log.e("ITEM : ", "IS $currentItem")

        holder.bind(currentItem, itemAdapterEdit, itemAdapterDelete)
    }

    class ViewHolder(val binding: ItemStyleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Items, itemAdapterEdit: ItemsAdapterEdit, adapterDelete: ItemsAdapterDelete) {

            // initialise every item with certain features
            binding.listTitle.text = currentItem.name
            binding.itemCheckbox.isChecked = currentItem.itemState

            // function for checking an item, this.function -> interface -> override function
            binding.itemCheckbox.setOnClickListener {
                if(currentItem.itemState) {
                    itemAdapterEdit.editItem(currentItem.id, false)
                } else {
                    itemAdapterEdit.editItem(currentItem.id, true)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStyleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class ShowNotesDiffCallback : DiffUtil.ItemCallback<Items>() {
    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem == newItem
    }
}

interface ItemsAdapterDelete {
    fun deleteItem(currentItem: Items)
}

interface ItemsAdapterEdit {
    fun editItem(id: Int, newState: Boolean)
}