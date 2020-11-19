package com.example.noci.lists.input

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.database_lists.items.Items
import com.example.noci.databinding.ItemStyleBinding


const val EDIT_CHECKER: String = ""

class ShopNoteAdapter(private val adapterDelete: ItemsAdapterDelete) :
    ListAdapter<Items, ShopNoteAdapter.ViewHolder>(
        ShowNotesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        Log.e("ITEM : ", "IS $currentItem")

        holder.bind(currentItem, adapterDelete)
    }

    class ViewHolder(val binding: ItemStyleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Items, adapterDelete: ItemsAdapterDelete) {
            binding.listTitle.text = currentItem.name

            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // CREATE IN THE ITEM DATABASE A BOOLEAN TYPE, AFTER THE READING OF ALL DATA CHECK IF THE ITEMS ARE CHECKED OR NOT THEN SHOW THEM AS SUCH, ON EVERY ITEMCHECKBOX CLICK CHECK IF THE ITEM IS CHECKED, IF CHECKED THEN UNCHECK, ELSE CHECK
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
//            if(currentItem.listBool == true) {
//                binding.expand.visibility = View.VISIBLE
//            } else {
//                binding.expand.visibility = View.GONE
//            }

            // set each note's type if specified


//            binding.noteBase.setOnClickListener {
//                //Hawk.put(EDIT_CHECKER, "edit")
//
//                adapterInfo.editItem(currentItem)
//            }

            // function for deleting a note, this.function -> interface -> override function
            binding.itemCheckbox.setOnClickListener {
                //adapterDelete.deleteItem(currentItem)
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