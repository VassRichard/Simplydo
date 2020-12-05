package com.example.noci.notes

//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.noci.InputActivity
import com.example.noci.R
import com.example.noci.ThemeKey
import com.example.noci.database.Note
import com.example.noci.databinding.FragmentNotesBinding
import com.example.noci.setThemeKey
import com.example.noci.utils.MyApplication
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_notes.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class NotesFragment : Fragment(), NotesAdapterInfo, NotesAdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    private var currentNightMode by Delegates.notNull<Int>()

    private val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var threadChecker = false
    private val adapter = NotesAdapter(this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        binding.notesList.adapter = adapter

        Hawk.init(context).build()

        currentNightMode = AppCompatDelegate.getDefaultNightMode()
        val theme = Hawk.get<String>(MODE_ENABLER, "light_mode")

        // the problem is here
//        if(ThemeKey.theme == "dark_mode") {
//            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            //Hawk.put(MODE_ENABLER, "dark_mode")
//            setThemeKey("dark_mode")
//        } else if(ThemeKey.theme == "light_mode") {
//            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            //Hawk.put(MODE_ENABLER, "light_mode")
//            setThemeKey("light_mode")
//        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (!threadChecker) {
            thread.start()
        }

        //val todayDate =
        //LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yyyy")).toString()

        //notesViewModel.getTodayTasksCount(todayDate)

        notesViewModel.onClickedSwitch.observe(viewLifecycleOwner, Observer {
            if (it) {
                //val currentNightMode =
                //resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                if (currentNightMode == AppCompatDelegate.MODE_NIGHT_NO) {
                    //Hawk.put(MODE_ENABLER, "dark_mode")
                    setThemeKey("dark_mode")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    //activity?.let { it1 -> recreate(it1) }
                    notesViewModel.dayNightResetter()
                } else if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    //Hawk.put(MODE_ENABLER, "light_mode")
                    setThemeKey("light_mode")
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    //activity?.let { it1 -> recreate(it1) }
                    notesViewModel.dayNightResetter()
                }
            }
        })

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer
        {
            if (it.isEmpty()) {
                binding.emptyListTitle.visibility = View.VISIBLE
                binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
                binding.emptyListDescription.visibility = View.INVISIBLE
            }
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

            Hawk.delete(EDIT_CHECKER)
        })

        notesViewModel.goToInput.observe(viewLifecycleOwner, Observer
        {
            if (it == true) {

                if (binding.notesList.visibility == View.VISIBLE) {
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

    // Thread function that runs in another Thread, not blocking the interface and calculates today's date
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

                    if (binding.dayHeader.text != formattedDate) {
                        binding.dayHeader.text = formattedDate
                    }
                }
            } catch (e: InterruptedException) {
            }
        }
    }

    // override function for editItem interface defined in NotesAdapter, it also transfers a Bundle(that holds the currentItem's attributes) into the InputActivity
    override fun editItem(currentItem: Note) {
        val intent = Intent(context, InputActivity::class.java)

        intent.putExtra("note", currentItem)
        startActivity(intent)
    }

    // override function for deleteItem interface defined in NotesAdapter
    override fun deleteItem(currentItem: Note) {
        notesViewModel.deleteFromLocalDB(currentItem)
    }

    // function that accesses the InputActivity interface
    fun onAddNote() {
        val intent = Intent(context, InputActivity::class.java)

        startActivity(intent)
    }

}