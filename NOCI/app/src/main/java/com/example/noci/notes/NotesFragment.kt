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
import java.util.*

const val SWITCH_CHECKER: String = ""


class NotesFragment : Fragment(), AdapterInfo, AdapterDelete {

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
        //binding.listsList.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if (!threadChecker) {
            thread.start()
        }



        notesViewModel.onClickedSwitch.observe(viewLifecycleOwner, Observer {
//            var theme = Hawk.get<String>(SWITCH_CHECKER)
//            if (theme == "light_mode") {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                //activity?.setTheme(R.style.AppThemeDark)
//                //activity?.startActivity(Intent(context, MainActivity::class.java))
//                //activity?.finish()
//                Hawk.put(SWITCH_CHECKER, "dark_mode")
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                //activity?.setTheme(R.style.AppTheme)
//                //startActivity(Intent(context, MainActivity::class.java))
//                //activity?.finish()
//                Hawk.put(SWITCH_CHECKER, "light_mode")
//            }
        })


        binding.notesList.visibility = View.VISIBLE
        binding.notesButton.visibility = View.VISIBLE

//        val todayDate =
//            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

        //notesViewModel.getTodayTasksCount(todayDate)

        notesViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.emptyListTitle.visibility = View.VISIBLE
                binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                binding.emptyListTitle.visibility = View.INVISIBLE
                binding.emptyListDescription.visibility = View.INVISIBLE

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

                Toast.makeText(context, "Today you have $todayTasks", Toast.LENGTH_LONG).show()

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

//        // WORK MORE
//        notesViewModel.goToList.observe(viewLifecycleOwner, Observer {
//            if(it) {
//                binding.notesList.visibility = View.GONE
//                binding.notesButton.visibility = View.GONE
//                binding.listsList.visibility = View.VISIBLE
//                binding.listsButton.visibility = View.VISIBLE
//
//                //binding.emptyListTitle.visibility = View.GONE
//                //binding.emptyListDescription.visibility = View.GONE
//
//                binding.notes.visibility = View.GONE
//                binding.lists.visibility = View.VISIBLE
//
//            }
//            if(!it) {
//                binding.listsList.visibility = View.GONE
//                binding.listsButton.visibility = View.GONE
//
//                binding.listsList.visibility = View.INVISIBLE
//                binding.listsButton.visibility = View.INVISIBLE
//
//                binding.notesList.visibility = View.VISIBLE
//                binding.notesButton.visibility = View.VISIBLE
//
//                //binding.emptyListTitle.visibility = View.VISIBLE
//                //binding.emptyListDescription.visibility = View.VISIBLE
//
//                binding.notes.visibility = View.VISIBLE
//                binding.lists.visibility = View.GONE
//
//                binding.lists.visibility = View.INVISIBLE
//
//            }
//        })

        notesViewModel.switch.observe(viewLifecycleOwner, Observer {
            binding.notesList.visibility = View.GONE

            binding.listsList.visibility = View.VISIBLE
        })

//        notesViewModel.goToLists.observe(viewLifecycleOwner, Observer {
//            if(it == true) {
//                val intent = Intent(context, InputActivity::class.java)
//
//                startActivity(intent)
//            }
//        })

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(notes_list)

//        binding.mainPage.setOnTouchListener(object : OnSwipeTouchListener(requireContext()){
//            override fun onSwipeRight() {
//                super.onSwipeRight()
//
//                val intent = Intent(context, InputActivity::class.java)
//
//                startActivity(intent)
//            }
//
//            override fun onSwipeLeft() {
//                super.onSwipeLeft()
//            }
//        })

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

//open class OnSwipeTouchListener(ctx: Context?) : View.OnTouchListener {
//    private val gestureDetector: GestureDetector = GestureDetector(ctx, GestureListener())
//
//    override fun onTouch(v: View, event: MotionEvent): Boolean {
//        return gestureDetector.onTouchEvent(event)
//    }
//
//    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
//        private val SWIPE_THRESHOLD = 100
//        private val SWIPE_VELOCITY_THRESHOLD = 100
//
//        override fun onDown(e: MotionEvent): Boolean {
//            return true
//        }
//
//        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
//            var result = false
//            try {
//                val diffY: Float = e2.y - e1.y
//                val diffX: Float = e2.x - e1.x
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                        if (diffX > 0) {
//                            onSwipeRight()
//                        } else {
//                            onSwipeLeft()
//                        }
//                        result = true
//                    }
//                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
//                    if (diffY > 0) {
//                        onSwipeBottom()
//                    } else {
//                        onSwipeTop()
//                    }
//                    result = true
//                }
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//            }
//            return result
//        }
//    }
//
//    open fun onSwipeRight() {}
//    open fun onSwipeLeft() {}
//    open fun onSwipeTop() {}
//    open fun onSwipeBottom() {}
//}
