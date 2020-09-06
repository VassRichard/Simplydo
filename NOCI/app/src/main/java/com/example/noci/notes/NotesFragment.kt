package com.example.noci.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.databinding.FragmentNotesBinding
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NotesFragment : Fragment(), AdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    private var threadChecker = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        val adapter = NotesAdapter(this)
        binding.notesList.adapter = adapter

        notesViewModel.goToInput.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(R.id.action_notesFragment_to_inputFragment)
                notesViewModel.resetGoToInput()
            }
        })

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it as ArrayList<Note>)
        })

        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(notes_list)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if(threadChecker != true)
        {
            thread.start()
        }
    }

    private val thread: Thread = object : Thread() {
        override fun run() {
            try {
                threadChecker = true
                while (!this.isInterrupted) {
                    sleep(1000)
                    val c: Calendar = Calendar.getInstance()

                    val df = SimpleDateFormat("dd/MMM/yyyy HH:mm")
                    val formattedDate: String = df.format(c.time)

                    binding.notesTitle.text = formattedDate
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun onDeleteNote(currentItem: Note) {
        notesViewModel.deleteFromLocalDB(currentItem)
    }
}