package com.example.noci.notes

//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.InputActivity
import com.example.noci.databinding.FragmentNotesBinding
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val SWITCH_CHECKER: String = ""


class NotesFragment : Fragment(), NotesAdapterInfo, NotesAdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    private val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var threadChecker = false
    private val adapter = NotesAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        binding.notesList.adapter = adapter

        Hawk.init(this.context).build()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (!threadChecker) {
            thread.start()
        }

        binding.notesList.visibility = View.VISIBLE
        binding.notesButton.visibility = View.VISIBLE

        //val todayDate =
            //LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yyyy")).toString()

        //notesViewModel.getTodayTasksCount(todayDate)

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.emptyListTitle.visibility = View.VISIBLE
                binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
                binding.emptyListDescription.visibility = View.INVISIBLE

                // show the amount of tasks today
//                val calendar = Calendar.getInstance()
//                val day = calendar.get(Calendar.DAY_OF_MONTH)
//                val month = calendar.get(Calendar.MONTH)
//                val year = calendar.get(Calendar.YEAR)
//                val todayDate = "" + day + " " + MONTHS[month] + " " + year
//
//                var todayTasks: Int = 0
//
//                for (item in it) {
//                    //Log.e("TAG ITEM : ", "$item")
//                    if (todayDate == item.noteDate) {
//                        todayTasks += 1
//                        //Log.e("TODAY", "TODAY")
//                    }
//                }
//
//                Toast.makeText(context, "Today you have $todayTasks", Toast.LENGTH_LONG).show()

                it?.let {
                    adapter.submitList(it)
                }
            }
        })

        notesViewModel.goToInput.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                if(binding.notesList.visibility == View.VISIBLE) {
                    onAddNote()
                    notesViewModel.resetGoToInput()
                } else {
                    Toast.makeText(context, "YOLO BOY", Toast.LENGTH_LONG).show()

                }

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

                    binding.dayHeader.text = formattedDate
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun editItem(currentItem: Note) {
        val intent = Intent(context, InputActivity::class.java)

        intent.putExtra("note", currentItem)
        startActivity(intent)
    }

    override fun deleteItem(currentItem: Note) {
        notesViewModel.deleteFromLocalDB(currentItem)
    }

    fun onAddNote() {
        val intent = Intent(context, InputActivity::class.java)

        startActivity(intent)
    }

}