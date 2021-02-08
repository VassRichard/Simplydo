package com.example.noci.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.InputActivity
import com.example.noci.ListsActivity
import com.example.noci.NotesActivity
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel

    private var threadChecker = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.mainViewModel = mainViewModel

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (!threadChecker) {
            thread.start()
        }



        mainViewModel.onGoToNotes.observe(viewLifecycleOwner, Observer {
            if(it) {
                goToNotes()
            }
        })

        mainViewModel.onGoToLists.observe(viewLifecycleOwner, Observer {
            if(it) {
                goToLists()
            }
        })
    }

    private
    val thread: Thread = object : Thread() {
        override fun run() {
            try {
                threadChecker = true
                while (!this.isInterrupted) {
                    //sleep(1000)
                    val c: Calendar = Calendar.getInstance()

                    val df = SimpleDateFormat("EEEE", Locale.ENGLISH)
                    val formattedDate: String = df.format(c.time)

                    if (binding.currentDay.text != formattedDate) {
                        binding.currentDay.text = formattedDate
                    }
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    fun goToNotes() {
        val intent = Intent(context, NotesActivity::class.java)

        startActivity(intent)
    }

    fun goToLists() {
        val intent = Intent(context, ListsActivity::class.java)

        startActivity(intent)
    }
}