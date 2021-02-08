package com.example.noci.lists

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.ListsInputActivity
import com.example.noci.R
import com.example.noci.database_lists.ItemList
import com.example.noci.databinding.FragmentListsBinding
import com.orhanobut.hawk.Hawk
import java.lang.reflect.Array.newInstance
import java.text.SimpleDateFormat
import java.util.*

import androidx.fragment.app.commit
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

const val LIST_TITLE: String = ""

class ListFragment : Fragment(), AdapterInfo, AdapterDelete {

    private lateinit var binding: FragmentListsBinding
    private lateinit var listViewModel: ListViewModel

    private val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var threadChecker = false

    private val adapter = ListsAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lists, container, false)
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        binding.listViewModel = listViewModel

        binding.notesList.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // starting the thread that calculated todays date
        if (!threadChecker) {
            thread.start()
        }

//        val todayDate =
//            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

        //notesViewModel.getTodayTasksCount(todayDate)

        // read all the lists from the database
        listViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.emptyListsTitle.visibility = View.VISIBLE
                binding.emptyListsDescription.visibility = View.VISIBLE
            } else {
                binding.emptyListsTitle.visibility = View.INVISIBLE
                binding.emptyListsDescription.visibility = View.INVISIBLE
            }

            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val todayDate = "" + day + " " + MONTHS[month] + " " + year

            var todayTasks: Int = 0

            for (item in it) {
                //Log.e("TAG ITEM : ", "$item")
                if (todayDate == item.noteDate) {
                    todayTasks += 1
                    //Log.e("TODAY", "TODAY")
                }
            }

            //Toast.makeText(context, "Today you have $todayTasks", Toast.LENGTH_LONG).show()

            it?.let {
                adapter.submitList(it)
            }

            Hawk.delete(ITEM_DELETER_CHECKER)
        })

        // on buton press, show customDialog
        listViewModel.booleanChecker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
//                getFragmentManager()?.let { it1 ->
//                    CustomDialog.newInstance(getString(R.string.label_logout), getString(R.string.msg_logout)).show(
//                        it1, CustomDialog.TAG)
//                }
                //listViewModel.onGoToReset()

                val listTitle = binding.listsTitle.text.toString()

                if (listTitle != "") {
                    listViewModel.addNote(listTitle, "noteDate")
                    binding.listsTitle.setText("")
                }
            }
        })

        //
        listViewModel.goToInput.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                if (binding.notesList.visibility == View.VISIBLE) {
                    onAddNote()
                    listViewModel.resetGoToInput()
                } else {
                    Toast.makeText(context, "YOLO BOY", Toast.LENGTH_LONG).show()

                }

            }
        })

        //
        listViewModel.switch.observe(viewLifecycleOwner, Observer {
            binding.notesList.visibility = View.GONE

            //binding.listsList.visibility = View.VISIBLE
        })


//        notesViewModel.goToLists.observe(viewLifecycleOwner, Observer {
//            if(it == true) {
//                val intent = Intent(context, InputActivity::class.java)
//
//                startActivity(intent)
//            }
//        })

        //val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        //itemTouchHelper.attachToRecyclerView(notes_list)

    }

    fun goToInput() {
        val intent = Intent(context, ListsInputActivity::class.java)

        startActivity(intent)
    }

    // the function that calculates todays date
    private val thread: Thread = object : Thread() {
        override fun run() {
            try {
                threadChecker = true
                while (!this.isInterrupted) {
                    sleep(1000)
                    val c: Calendar = Calendar.getInstance()

                    val df = SimpleDateFormat("EEEE", Locale.ENGLISH)
                    val formattedDate: String = df.format(c.time)

                    //binding.notesTitle.text = formattedDate
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    // edit list function
    override fun editItem(currentItem: ItemList) {
        val intent = Intent(context, ListsInputActivity::class.java)

        intent.putExtra("list", currentItem)
        startActivity(intent)
    }

    // delete list function
    override fun deleteItem(currentItem: ItemList) {
        listViewModel.deleteListFromDB(currentItem)
    }

    // add note function
    fun onAddNote() {
        val intent = Intent(context, ListsInputActivity::class.java)

        startActivity(intent)
    }
}