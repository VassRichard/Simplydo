package com.example.noci.lists

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.database_lists.ItemList
import com.example.noci.databinding.ListsStyleBinding
import com.orhanobut.hawk.Hawk

const val CURRENT_LIST: String = ""

class ListsAdapter(private val adapterInfo: ListFragment, private val adapterDelete: ListFragment) :
    ListAdapter<ItemList, ListsAdapter.ViewHolder>(
        ShowNotesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        Log.e("ITEM : ", "IS $currentItem")

        holder.bind(currentItem, adapterInfo, adapterDelete)
    }

    class ViewHolder(val binding: ListsStyleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: ItemList, adapterInfo: ListFragment, adapterDelete: ListFragment) {
            binding.noteTitle.text = currentItem.title

            binding.noteBase.setOnClickListener {
                Hawk.put(CURRENT_LIST, currentItem.id.toString())
                Log.e("ID IS ", currentItem.id.toString())

                adapterInfo.editItem(currentItem)
            }

            // function for deleting a note, this.function -> interface -> override function
            binding.deleteNote.setOnClickListener {
                adapterDelete.deleteItem(currentItem)
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListsStyleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class ShowNotesDiffCallback : DiffUtil.ItemCallback<ItemList>() {
    override fun areItemsTheSame(oldItem: ItemList, newItem: ItemList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemList, newItem: ItemList): Boolean {
        return oldItem == newItem
    }
}

interface AdapterInfo {
    fun editItem(currentItem: ItemList)
}

interface AdapterDelete {
    fun deleteItem(currentItem: ItemList)
}