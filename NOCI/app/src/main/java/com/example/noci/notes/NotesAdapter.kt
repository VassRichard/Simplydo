package com.example.noci.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.R
import com.example.noci.database.Note
import kotlinx.android.synthetic.main.fragment_input.view.*
import kotlinx.android.synthetic.main.notes_list.view.*

class NotesAdapter: RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var noteList = listOf<Note>()
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
    }

    override fun getItemCount() = noteList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.note_title)
        //val description: TextView = itemView.findViewById(R.id.add_description)
    }

    fun setData(note : List<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }

}