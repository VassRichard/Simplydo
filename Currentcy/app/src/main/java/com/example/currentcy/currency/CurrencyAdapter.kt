package com.example.currentcy.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currentcy.databinding.CurrencyStyleBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CurrencyAdapter(private val adapterInfo: NotesAdapterInfo):
    ListAdapter<CurrencyData, CurrencyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem, adapterInfo)
    }

    class ViewHolder(val binding: CurrencyStyleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: CurrencyData, adapterInfo: NotesAdapterInfo) {
            //binding.noteTitle.text = currentItem.title

            binding.noteBase.setOnClickListener {
//                Hawk.put(EDIT_CHECKER, "edit")
//
//                adapterInfo.editItem(currentItem)
            }

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CurrencyStyleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

interface NotesAdapterInfo {
    fun editItem(currentItem: CurrencyData)
}