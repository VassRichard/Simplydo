package com.example.noci.notes.input

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noci.R
import com.example.noci.databinding.FragmentInputBinding
import kotlinx.android.synthetic.main.fragment_input.*

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

            if (TextUtils.isEmpty(noteTitle)) {
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(noteDescription)) {
                Toast.makeText(context, "Description field can't be empty!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                inputViewModel.insertNote(noteTitle, noteDescription)
                findNavController().navigate(R.id.action_inputFragment_to_notesFragment)
            }
        })

        return binding.root
    }

    override fun onPause() {
        super.onPause()

        // Clear all value here
        binding.addTitle.setText("")
        binding.addDescription.setText("")
    }
}