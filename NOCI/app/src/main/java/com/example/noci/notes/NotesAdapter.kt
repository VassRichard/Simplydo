package com.example.noci.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.R
import com.example.noci.database.Note
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE


class NotesAdapter(private val adapterDelete: AdapterDelete) :
    ListAdapter<Note, NotesAdapter.ViewHolder>(
        ShowNotesDiffCallback()
    ) {

    private var noteList = arrayListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.notes_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = noteList[position]

        when (currentItem.type) {
            0 -> {
                holder.type.setImageResource(R.drawable.ic_health)
            }
            1 -> {
                holder.type.setImageResource(R.drawable.ic_relationships)
            }
            2 -> {
                holder.type.setImageResource(R.drawable.ic_career)
            }
            3 -> {
                holder.type.setImageResource(R.drawable.ic_personal)
            }
            4 -> {
                holder.type.setImageResource(R.drawable.ic_social_v1)
            }
            5 -> {
                holder.type.setImageResource(R.drawable.ic_recreation_v3)
            }
        }
        holder.title.text = currentItem.title

        holder.date.text = currentItem.date

        holder.deleteButton.setOnClickListener {
            deleteItem(position)
            adapterDelete.onDeleteNote(currentItem)
        }

        //holder.type.onHo
    }

    override fun getItemCount() = noteList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title)
        val date: TextView = itemView.findViewById(R.id.note_date)
        val type: ImageView = itemView.findViewById(R.id.note_type)

        val deleteButton: ImageView = itemView.findViewById(R.id.delete_note)
    }

    fun setData(note: ArrayList<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }

    fun deleteItem(pos: Int) {
        this.noteList.removeAt(pos)
        notifyItemRemoved(pos)
        notifyDataSetChanged()
    }
}

class ShowNotesDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}

interface AdapterDelete {
    fun onDeleteNote(noteId: Note)
}