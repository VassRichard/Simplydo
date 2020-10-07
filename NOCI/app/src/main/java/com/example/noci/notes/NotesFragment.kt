package com.example.noci.notes

//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.InputActivity
import com.example.noci.databinding.FragmentNotesBinding
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*


class NotesFragment : Fragment(), AdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    private val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var threadChecker = false
    private val adapter = NotesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        binding.notesList.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (!threadChecker) {
            thread.start()
        }

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.emptyListTitle.visibility = View.VISIBLE
                binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
                binding.emptyListDescription.visibility = View.INVISIBLE
            }
        })

        notesViewModel.goToInput.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                onAddNote()
                notesViewModel.resetGoToInput()
            }
        })

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val todayDate = "" + day + " " + MONTHS[month] + " " + year

            for (item in it) {
                Log.e("TAG ITEM : ", "$item")
                if (todayDate == item.noteDate) {
                    Log.e("TODAY", "TODAY")
                }
            }

            it?.let {
                adapter.submitList(it)
            }
        })

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(notes_list)

    }

    private val thread: Thread = object : Thread() {
        override fun run() {
            try {
                threadChecker = true
                while (!this.isInterrupted) {
                    sleep(1000)
                    val c: Calendar = Calendar.getInstance()

                    val df = SimpleDateFormat("EEEE", Locale.ENGLISH)
                    val formattedDate: String = df.format(c.time)

                    binding.notesTitle.text = formattedDate
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun deleteItem(currentItem: Note) {
        notesViewModel.deleteFromLocalDB(currentItem)
    }

    fun onAddNote() {
        val intent = Intent(context, InputActivity::class.java)

        startActivity(intent)
    }

}
