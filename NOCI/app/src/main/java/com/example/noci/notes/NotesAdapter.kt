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
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.notes_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = noteList[position]

        holder.title.text = currentItem.title

//        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//        val text: String = currentItem.date.format(formatter)
        holder.date.text = currentItem.date

        holder.deleteButton.setOnClickListener {
            deleteItem(position)
            //adapterDelete.onDeleteNote(currentItem.id)
            adapterDelete.onDeleteNote(currentItem)
        }

        //        holder.deleteButton.setOnClickListener {
//            adapterListener.onCategoryClicked(currentItem)
//            Log.e("BLA", "BLA")
//            Log.e("BLA", "BLA")
//        }

//        holder.title.setOnDragListener(object : SwipeLeftRightCallback.Listener {
//            override fun onSwipedLeft(position: Int) {
//                //noteList.remove(position)
//                Log.e("SWIPED", "AT $position")
//                //notifyDataSetChanged()
//            }
//
//            override fun onSwipedRight(position: Int) {
//                TODO("Not yet implemented")
//            }
//        })
    }

    override fun getItemCount() = noteList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title)
        val date: TextView = itemView.findViewById(R.id.note_date)

        val deleteButton: ImageView = itemView.findViewById(R.id.delete_note)
        //val description: TextView = itemView.findViewById(R.id.add_description)
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