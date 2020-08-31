package com.example.noci.notes.input

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noci.R
import com.example.noci.databinding.FragmentInputBinding
import com.example.noci.notes.NotesAdapter

class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    private lateinit var inputViewModel: InputViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)
        inputViewModel = ViewModelProvider(this).get(InputViewModel::class.java)

        binding.inputViewModel = inputViewModel

        inputViewModel.insertInitializer.observe(viewLifecycleOwner, Observer {
            //binding.addTitle.toString() != "" && binding.addDescription.toString() != ""
            val noteTitle = binding.addTitle.text.toString()
            val noteDescription = binding.addDescription.text.toString()

            if(!TextUtils.isEmpty(noteTitle) && !TextUtils.isEmpty(noteDescription)) {
                inputViewModel.insertNote(noteTitle, noteDescription)
                findNavController().navigate(R.id.action_inputFragment_to_notesFragment)
            }
        })

        return binding.root
    }
}