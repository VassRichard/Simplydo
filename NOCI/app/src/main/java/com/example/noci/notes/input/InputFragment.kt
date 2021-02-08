package com.example.noci.notes.input

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.NotesActivity
import com.example.noci.R
import com.example.noci.databinding.FragmentInputBinding
import com.example.noci.typeKey
import com.orhanobut.hawk.Hawk
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates


class InputFragment : Fragment() {
    private lateinit var binding: FragmentInputBinding
    private lateinit var inputViewModel: InputViewModel

    val MONTHS =
        arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    private var currentNightMode by Delegates.notNull<Int>()

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

        val details = InputFragmentArgs.fromBundle(requireArguments()).note

        if (details != null) {
            binding.addTitle.setText(details.title)

            binding.addDate.isEnabled = false
            binding.addDate.isClickable = false
            binding.addDate.text = details.noteDate

            binding.addButton.text = "SAVE NOTE"
        }

        if (typeKey.getMark()) {
            binding.check.isChecked = true
            noteTypeMarker(typeKey.getMark())
        } else {
            binding.check.isChecked = false
            noteTypeMarker(typeKey.getMark())
        }

        inputViewModel.insertInitializer.observe(viewLifecycleOwner, Observer {
            val noteTitle = binding.addTitle.text.toString()
            val noteDate = binding.addDate.text.toString()
            if (TextUtils.isEmpty(noteTitle)) {
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT)
                    .show()
                binding.addTitle.requestFocus()
            } else if (TextUtils.isEmpty(noteDate)) {
                Toast.makeText(context, "Date field can't be empty!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (details != null) {
//                    if you want to update noteDate
//                    inputViewModel.updateNote(details.id, noteTitle, noteDate)
                    inputViewModel.updateNote(details.id, noteTitle)
                } else {
                    inputViewModel.addNote(noteTitle, noteDate)
                }

                onGoBack()
            }
        })

        inputViewModel.onMarkType.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (typeKey.getMark()) {
                    typeKey.setMark(false)
                } else {
                    typeKey.setMark(true)
                }

                noteTypeMarker(typeKey.getMark())

                inputViewModel.onMarkTypeReset()
            }
        })

        inputViewModel.noteOpacity.observe(viewLifecycleOwner, Observer {
            when (it) {
                0 -> {
                    binding.qualityZeroImage.alpha = 0.75F
                    noteOpacityChanger(it)
                }
                1 -> {
                    binding.qualityOneImage.alpha = 0.75F
                    noteOpacityChanger(it)
                }
                2 -> {
                    binding.qualityTwoImage.alpha = 0.75F
                    noteOpacityChanger(it)
                }
                3 -> {
                    binding.qualityThreeImage.alpha = 0.75F
                    noteOpacityChanger(it)
                }
                4 -> {
                    binding.qualityFourImage.alpha = 0.75F
                    noteOpacityChanger(it)
                }
                5 -> {
                    binding.qualityFiveImage.alpha = 0.75F
                    noteOpacityChanger(it)
                }
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
                this.context?.let {
                    DatePickerDialog(
                        it,
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

    fun noteTypeMarker(mark: Boolean) {
        if (mark) {
            binding.qualityZeroImage.visibility = View.VISIBLE
            binding.qualityOneImage.visibility = View.VISIBLE
            binding.qualityTwoImage.visibility = View.VISIBLE
            binding.qualityThreeImage.visibility = View.VISIBLE
            binding.qualityFourImage.visibility = View.VISIBLE
            binding.qualityFiveImage.visibility = View.VISIBLE
        } else {
            binding.qualityZeroImage.visibility = View.INVISIBLE
            binding.qualityOneImage.visibility = View.INVISIBLE
            binding.qualityTwoImage.visibility = View.INVISIBLE
            binding.qualityThreeImage.visibility = View.INVISIBLE
            binding.qualityFourImage.visibility = View.INVISIBLE
            binding.qualityFiveImage.visibility = View.INVISIBLE
        }
    }

    fun noteOpacityChanger(typeNumber: Int) {
        if (typeNumber == 0) {
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if (typeNumber == 1) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if (typeNumber == 2) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if (typeNumber == 3) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if (typeNumber == 4) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if (typeNumber == 5) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
        }
    }

    override fun onPause() {
        super.onPause()

    }

    fun onGoBack() {
        val intent = Intent(context, NotesActivity::class.java)

        startActivity(intent)
    }

}