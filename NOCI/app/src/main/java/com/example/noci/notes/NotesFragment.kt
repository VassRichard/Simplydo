package com.example.noci.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.databinding.FragmentNotesBinding
import kotlinx.android.synthetic.main.fragment_notes.*


class NotesFragment : Fragment(), AdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

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

    override fun onDeleteNote(currentItem: Note) {
        notesViewModel.deleteFromLocalDB(currentItem)
    }
}