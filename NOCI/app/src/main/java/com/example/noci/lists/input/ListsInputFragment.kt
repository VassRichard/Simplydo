package com.example.noci.lists.input

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.*
import com.example.noci.database_lists.items.Items
import com.example.noci.databinding.FragmentInputListsBinding
import com.example.noci.lists.ITEM_DELETER_CHECKER
import com.orhanobut.hawk.Hawk


class ListsInputFragment : Fragment(), ItemsAdapterEdit, ItemsAdapterDelete {

    private lateinit var binding: FragmentInputListsBinding
    private lateinit var inputViewModel: ListsInputViewModel

    val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private val adapter = ItemsAdapter(this, this)

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

        // get arguments from the bundle that came from the ListFragment via navigation
        val details = ListsInputFragmentArgs.fromBundle(requireArguments()).lists

        if (details != null) {
            binding.addTitle.text = details.title
            binding.editTitle.setText(details.title)
        }

        // read all items from the database of the specific list
        inputViewModel.itemsReadAll?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }

            Hawk.delete(ITEM_DELETER_CHECKER)
        })

        // insert item to the database
        inputViewModel.insertInitializer.observe(viewLifecycleOwner, Observer {
            val noteTitle = binding.addTitle.text.toString()

            if (TextUtils.isEmpty(noteTitle)) {
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT).show()
            } else {
                onGoBack()
            }
        })

        // update title of the list
        inputViewModel.onChangeTitle.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.addTitle.visibility = View.INVISIBLE
                binding.editTitle.visibility = View.VISIBLE

                binding.editTitle.requestFocus()
            }
            binding.editTitle.onFocusChangeListener =
                View.OnFocusChangeListener() { view: View, b: Boolean ->
                    if (!binding.editTitle.hasFocus()) {
                        binding.addTitle.visibility = View.VISIBLE
                        binding.editTitle.visibility = View.INVISIBLE

                        val newTitle: String = binding.editTitle.text.toString()

                        binding.addTitle.text = newTitle

                        // check if this goes as intended
                        if (details != null) {
                            inputViewModel.updateTitle(details.id, newTitle)
                        }
                    }
                }
        })

        // go back to main
        inputViewModel.onGoBackToMain.observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(context, NotesActivity::class.java)

                startActivity(intent)
            }
        })

//        inputViewModel.insertDateInitializer.observe(viewLifecycleOwner, Observer {
//            val calendar = Calendar.getInstance()
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//            val month = calendar.get(Calendar.MONTH)
//            val year = calendar.get(Calendar.YEAR)
//
//            // date picker dialog
//            val datepickerdialog: DatePickerDialog? =
//                this.context?.let { it1 ->
//                    DatePickerDialog(
//                        it1,
//                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//
//                            // Display Selected date in textbox
//                            //binding.addDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + " " + year)
//
//                        },
//                        year,
//                        month,
//                        day
//                    )
//                }
//
//            datepickerdialog!!.show()
//        })

        // activate the _addToListBool value when user pressed Enter, which adds the item into database / the function below
        binding.itemName.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                inputViewModel.addToList()
                //binding.itemName.requestFocus()
            }
            true
        })

        // adds item in the database of the specific list
        inputViewModel.addToListBool.observe(viewLifecycleOwner, Observer {
            if (it) {
                val item = binding.itemName.text.toString()
                val listId = details?.id

                Log.e(" NOTE : ", " $item and $listId")

                if (item.isNotEmpty()) {
                    if (listId != null) {
                        inputViewModel.addNote(item, listId)
                    }
                }

                binding.itemName.setText("")
            }
        })

        // sets the ItemState variable in the item database of the specific list to true/false depending on already stored value
        inputViewModel.onSelectAllBool.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (details != null) {
                    if (!SelectKey.getSelect()) {
                        SelectKey.setSelect(true)
                        inputViewModel.selectAllItems(details.id, true)
                    } else {
                        SelectKey.setSelect(false)
                        inputViewModel.selectAllItems(details.id, false)
                    }
                }
            }
        })

        inputViewModel.onDeleteSelectedBool.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (details != null) {
                    inputViewModel.onDeleteSelectedItems(details.id)
                }
            }
        })

//        inputViewModel.onCopyDataBool.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                if (details != null) {
//                    inputViewModel.copyDataToClip(details.id)
//                }
//            }
//        })

        binding.itemName.setOnFocusChangeListener { view: View, b: Boolean ->
//            if(!view.hasFocus()) {
//                binding.itemName.requestFocus()
//                val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, SOFT_INPUT_STATE_VISIBLE)
        }
    }

    // override for interfaces in the ItemsAdapter class
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