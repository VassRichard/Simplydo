package com.example.noci.notes.input

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.NotesActivity
import com.example.noci.R
import com.example.noci.databinding.FragmentInputBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    private lateinit var inputViewModel: InputViewModel

    val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)
        inputViewModel = ViewModelProvider(this).get(InputViewModel::class.java)

        binding.inputViewModel = inputViewModel

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.addDate.text =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")).toString()

        //Hawk.init(context).build()

        //val editChecker = Hawk.get<String>(EDIT_CHECKER)

        //if (editChecker == "edit") {
        val details = InputFragmentArgs.fromBundle(requireArguments()).note

        if (details != null) {
            binding.addButton.text = "SAVE NOTE"

            binding.addTitle.setText(details.title)
            binding.addDate.text = details.noteDate
        }
        //}

        inputViewModel.insertInitializer.observe(viewLifecycleOwner, Observer {
            //binding.addTitle.toString() != "" && binding.addDescription.toString() != ""
            val noteTitle = binding.addTitle.text.toString()
            val noteDate = binding.addDate.text.toString()
            //Hawk.put(EDIT_CHECKER, "nonEdit")
            //if (details != null) {
                //Log.e("NOTE TAG : ", "IS ${details.id} , ${details.title}, ${details.noteDate}, ${details.date}")
            //}
            if (TextUtils.isEmpty(noteTitle)) {
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(noteDate)) {
                Toast.makeText(context, "Date field can't be empty!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (details != null) {
                    inputViewModel.updateNote(details.id, noteTitle, noteDate)
                } else {
                    inputViewModel.addNote(noteTitle, noteDate)
                }

                onGoBack()
            }
        })

        inputViewModel.onGoBackToMain.observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(context, NotesActivity::class.java)

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
                            binding.addDate.setText("" + dayOfMonth + " " + MONTHS[monthOfYear] + " " + year)

                        },
                        year,
                        month,
                        day
                    )
                }

            datepickerdialog!!.show()
        })

    }

    override fun onPause() {
        super.onPause()

        // Clear all value here
        //binding.addTitle.setText("")
        //binding.addDate.setText("")
    }

    fun onGoBack() {
        val intent = Intent(context, NotesActivity::class.java)

        startActivity(intent)
    }

}