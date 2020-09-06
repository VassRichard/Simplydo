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

        if(currentItem.type == 0) {
            holder.type.setImageResource(R.drawable.ic_type_0)
        }
        else if(currentItem.type == 1) {
            holder.type.setImageResource(R.drawable.ic_type_1)
        }
        else if(currentItem.type == 2) {
            holder.type.setImageResource(R.drawable.ic_type_2)
        }
        else if(currentItem.type == 3) {
            holder.type.setImageResource(R.drawable.ic_type_3)
        }
        else if(currentItem.type == 4) {
            holder.type.setImageResource(R.drawable.ic_type_4)
        }
        else if(currentItem.type == 5) {
            holder.type.setImageResource(R.drawable.ic_type_5)
        }
        holder.title.text = currentItem.title

        holder.date.text = currentItem.date

        holder.deleteButton.setOnClickListener {
            deleteItem(position)
            adapterDelete.onDeleteNote(currentItem)
        }
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