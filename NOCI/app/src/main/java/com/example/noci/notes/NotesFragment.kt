package com.example.noci.notes

//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.noci.InputActivity
import com.example.noci.R
import com.example.noci.database.Note
import com.example.noci.databinding.FragmentNotesBinding
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_lists.*
import kotlin.properties.Delegates

class NotesFragment : Fragment(), NotesAdapterInfo, NotesAdapterDelete {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var notesViewModel: NotesViewModel

    private var currentNightMode by Delegates.notNull<Int>()

    //    private var threadChecker = false
    private val adapter = NotesAdapter(this, this)

//    val itemTouch = object : ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(adapter)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        binding.notesList.adapter = adapter

        currentNightMode = AppCompatDelegate.getDefaultNightMode()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

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
                    notesViewModel.goToInputNoteResetter()
                }
            }
        })

//        val itemTouchHelperCallback =
//            object :
//                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//
//                    return false
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    //adapter.adapterDelete.deleteItem(viewHolder.adapterPosition)
//                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
////                    adapter.notifyItemRemoved(viewHolder.position)
//                    adapter.notifyDataSetChanged()
//
//                    //val note: Note = adapter.getNoteAt(position)
//
//                    //notesViewModel.deleteFromLocalDB(note)
//                    //adapter.notifyItemRemoved(position)
////                    Toast.makeText(
////                        this@MainActivity,
////                        getString(R.string.note_deleted),
////                        Toast.LENGTH_SHORT
////                    ).show()
//
//                }
//            }



//        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(notes_list)
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