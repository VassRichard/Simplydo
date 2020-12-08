package com.example.noci.lists.input

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import com.example.noci.database.Note
import com.example.noci.database_lists.items.Items
import com.example.noci.databinding.FragmentInputListsBinding
import com.example.noci.lists.ITEM_DELETER_CHECKER
import com.orhanobut.hawk.Hawk
import java.util.*

class ListsInputFragment : Fragment(), ItemsAdapterEdit, ItemsAdapterDelete {

    private lateinit var binding: FragmentInputListsBinding
    private lateinit var inputViewModel: ListsInputViewModel

    val MONTHS =
    arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private val adapter = ShopNoteAdapter( this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input_lists, container, false)
        inputViewModel = ViewModelProvider(this).get(ListsInputViewModel::class.java)

        binding.inputViewModel = inputViewModel

        binding.showList.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //binding.addDate.text =
            //LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

        //Hawk.init(context).build()

        //val editChecker = Hawk.get<String>(EDIT_CHECKER)

        val itemsIdString = Hawk.get<String>(ITEM_DELETER_CHECKER)
        //val itemsIdInt = itemsIdString.toInt()

        //if (editChecker == "edit") {0
        val details = ListsInputFragmentArgs.fromBundle(requireArguments()).lists
        Log.e(" DET ", "$details")

        if (details != null) {
            //binding.addButton.text = "SAVE NOTE"

            binding.addTitle.text = details.title
            binding.editTitle.setText(details.title)
            //binding.addDate.text = details.noteDate
        }
        //}

        inputViewModel.itemsReadAll.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                //binding.emptyListTitle.visibility = View.VISIBLE
                //binding.emptyListDescription.visibility = View.VISIBLE
            } else {
                //binding.emptyListTitle.visibility = View.INVISIBLE
                //binding.emptyListDescription.visibility = View.INVISIBLE

                it?.let {
                    adapter.submitList(it)
                }
                Hawk.delete(ITEM_DELETER_CHECKER)
            }
        })

        inputViewModel.insertInitializer.observe(viewLifecycleOwner, Observer {
            //binding.addTitle.toString() != "" && binding.addDescription.toString() != ""
            val noteTitle = binding.addTitle.text.toString()
            //val noteDate = binding.addDate.text.toString()
            //Hawk.put(EDIT_CHECKER, "nonEdit")
            //if (details != null) {
            //Log.e("NOTE TAG : ", "IS ${details.id} , ${details.title}, ${details.noteDate}, ${details.date}")
            //}
            if (TextUtils.isEmpty(noteTitle)) {
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT).show()
            } else {
                //if //(details != null) {
                    //inputViewModel.updateNote(details.id, noteTitle)
                //} else {
                //inputViewModel.addNote(noteTitle)

                //}

                onGoBack()
            }
        })

        inputViewModel.onChangeTitle.observe(viewLifecycleOwner, Observer {
            if(it) {
                binding.addTitle.visibility = View.INVISIBLE
                binding.editTitle.visibility = View.VISIBLE

                binding.editTitle.requestFocus()
            }
            binding.editTitle.onFocusChangeListener = View.OnFocusChangeListener() { view: View, b: Boolean ->
                if(!binding.editTitle.hasFocus()) {
                    binding.addTitle.visibility = View.VISIBLE
                    binding.editTitle.visibility = View.INVISIBLE

                    val newTitle : String = binding.editTitle.text.toString()

                    binding.addTitle.text = newTitle

                    // check if this goes as intended
                    if (details != null) {
                        inputViewModel.updateTitle(details.id, newTitle)
                    }
                }
            }
        })

        inputViewModel.onGoBackToMain.observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(context, ListsInputActivity::class.java)

                startActivity(intent)
            }
        })

        inputViewModel.insertDateInitializer.observe(viewLifecycleOwner, Observer {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            // date picker dialog
            val datepickerdialog: DatePickerDialog? =
                this.context?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            // Display Selected date in textbox
                            //binding.addDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + " " + year)

                        },
                        year,
                        month,
                        day
                    )
                }

            datepickerdialog!!.show()
        })

        inputViewModel.addToListBool.observe(viewLifecycleOwner, Observer {
            if(it) {
                val subnote = binding.itemName.text.toString()
                val noteId = details?.id

                Log.e(" NOTE : ", " $subnote and $noteId")

                if (noteId != null && subnote != "") {
                    inputViewModel.addNote(subnote, noteId)
                }

                binding.itemName.setText("")
            }
        })
    }

    override fun editItem(id: Int, newState: Boolean) {
        inputViewModel.changeItemState(id, newState)
    }

    override fun deleteItem(currentItem: Items) {
        inputViewModel.deleteFromLocalDB(currentItem)
    }

    fun onGoBack() {
        val intent = Intent(context, ListsInputActivity::class.java)

        startActivity(intent)
    }

}