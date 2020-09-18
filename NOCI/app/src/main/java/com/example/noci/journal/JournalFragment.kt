package com.example.noci.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.noci.R
import com.example.noci.databinding.FragmentJournalBinding
import com.example.noci.notes.NotesViewModel

class JournalFragment : Fragment() {

    private lateinit var binding: FragmentJournalBinding
    private lateinit var journalViewModel: JournalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_journal, container, false)
        journalViewModel = ViewModelProvider(this).get(JournalViewModel::class.java)

        binding.journalViewModel = journalViewModel

        return binding.root
    }
}