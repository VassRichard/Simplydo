package com.example.noci.lists

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
import java.util.*

//const val LIST_TITLE: String = ""

class ListFragment : Fragment(), AdapterInfo, AdapterDelete{ //, ExampleDialogListener {

    private lateinit var binding: FragmentListsBinding
    private lateinit var listViewModel: ListViewModel

    private val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var threadChecker = false

    private val adapter = ListsAdapter(this, this)
//    private val listener = CustomDialog(this)

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
//            val todayDate = "" + day + " " + MONTHS[month] + " " + year

//            var todayTasks: Int = 0
//
//            for (item in it) {
//                //Log.e("TAG ITEM : ", "$item")
//                if (todayDate == item.noteDate) {
//                    todayTasks += 1
//                    //Log.e("TODAY", "TODAY")
//                }
//            }

            it?.let {
                adapter.submitList(it)
            }

            Hawk.delete(ITEM_DELETER_CHECKER)
        })

        // on buton press, show customDialog
        listViewModel.onAddBoolean.observe(viewLifecycleOwner, Observer {
            if (it == true) {
//                getFragmentManager()?.let { it1 ->
//                    CustomDialog.newInstance(
//                        getString(R.string.label_logout),
//                        getString(R.string.msg_logout)
//                    ).show(
//                        it1, CustomDialog.TAG
//                    )
//                }

                //listViewModel.onGoToReset()

//                val string = Hawk.get<String>(LIST_TITLE)

                val listTitle = binding.listsTitle.text.toString()

                if (listTitle == "") {
                    Toast.makeText(context, "The field must not be empty!", Toast.LENGTH_SHORT).show()
                    listViewModel.onAddResetter()
                } else {
                    listViewModel.addNote(listTitle, "noteDate")
                    binding.listsTitle.setText("")
                    listViewModel.onAddResetter()
                    // ADD DIRECT NAVIGATION TO THAT LIST
//                    Hawk.init(context).build()
//                    Hawk.put(PLM, listTitle)
//
//                    val intent = Intent(context, ListsInputActivity::class.java)
//                    startActivity(intent)
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
                    //Toast.makeText(context, "YOLO BOY", Toast.LENGTH_LONG).show()
                }
            }
        })

        //val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        //itemTouchHelper.attachToRecyclerView(notes_list)

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