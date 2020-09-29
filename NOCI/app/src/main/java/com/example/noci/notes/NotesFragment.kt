package com.example.noci.notes

//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread

import android.content.Intent
import android.os.Bundle
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
import com.example.noci.databinding.FragmentNotesBinding
import com.example.noci.InputActivity
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*


class NotesFragment : Fragment(), AdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

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

        if (threadChecker != true) {
            thread.start()
        }

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.emptyListTitle.visibility = View.VISIBLE
<<<<<<< HEAD
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
            }
        })

        notesViewModel.listChecker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.emptyListTitle.visibility = View.VISIBLE
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
=======
                binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
                binding.emptyListDescription.visibility = View.INVISIBLE
>>>>>>> parent of 86dc984... New app name/splash/logo
            }
        })

        notesViewModel.goToInput.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                onAddNote()
                notesViewModel.resetGoToInput()
            }
        })

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it as ArrayList<Note>)
        })

        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
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

    override fun onDeleteNote(currentItem: Note) {
        notesViewModel.deleteFromLocalDB(currentItem)
    }

    // transfer the clicked car's brand details to the activity which will call the brand's list of cars ( click on mazda, it will populate only with cars that belong to mazda )
    fun onAddNote() {
        val intent = Intent(context, InputActivity::class.java)

        startActivity(intent)

//        val intent = Intent(context, CarsActivity::class.java)
//        intent.putExtra("categoryId", categoryId)
//        startActivity(intent)
    }
}