package com.example.noci.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.databinding.NoteStyleBinding
import com.orhanobut.hawk.Hawk
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


const val EDIT_CHECKER: String = ""

class NotesAdapter(private val adapterInfo: NotesAdapterInfo, val adapterDelete: NotesAdapterDelete) :
    ListAdapter<Note, NotesAdapter.ViewHolder>(
        ShowNotesDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem, adapterInfo, adapterDelete)
    }

    class ViewHolder(val binding: NoteStyleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentItem: Note, adapterInfo: NotesAdapterInfo, adapterDelete: NotesAdapterDelete) {
            binding.noteTitle.text = currentItem.title

            // context for the overdueSignaler
            val overdueSignalerContext = binding.overdueSignaler.context

            // parsing the note's date from String to LocalDate format & getting current date
            val noteDate = LocalDate.parse(currentItem.noteDate, DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH))
            val currentDate = LocalDate.now()

            // show each note's status, Red - overdue/passed, Blue - upcoming, Green - today & if the note is today show date as "Today" else show the set date
            if (noteDate.isBefore(currentDate)) {
                binding.overdueSignaler.setImageDrawable(ContextCompat.getDrawable(overdueSignalerContext, R.drawable.overdue_yesterday))
                binding.noteDate.text = currentItem.noteDate
            } else if(noteDate.isAfter(currentDate)) {
                binding.overdueSignaler.setImageDrawable(ContextCompat.getDrawable(overdueSignalerContext, R.drawable.overdue_tomorrow))
                binding.noteDate.text = currentItem.noteDate
            } else {
                binding.overdueSignaler.setImageDrawable(ContextCompat.getDrawable(overdueSignalerContext, R.drawable.overdue_today))
                binding.noteDate.text = "Today"
            }

            // set each notes type if specified
            when (currentItem.type) {
                0 -> {
                    binding.noteType.setImageResource(R.drawable.measured_health)
                }
                1 -> {
                    binding.noteType.setImageResource(R.drawable.measured_relationships)
                }
                2 -> {
                    binding.noteType.setImageResource(R.drawable.measured_career)
                }
                3 -> {
                    binding.noteType.setImageResource(R.drawable.measured_personal)
                }
                4 -> {
                    binding.noteType.setImageResource(R.drawable.measured_love)
                }
                5 -> {
                    binding.noteType.setImageResource(R.drawable.measured_free_time)
                }
            }

            binding.noteBase.setOnClickListener {
                Hawk.put(EDIT_CHECKER, "edit")

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
                val binding = NoteStyleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
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

interface NotesAdapterInfo {
    fun editItem(currentItem: Note)
}

interface NotesAdapterDelete {
    fun deleteItem(currentItem: Note)
}