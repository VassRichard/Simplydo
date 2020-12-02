package com.example.noci.notes.input

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.NotesActivity
import com.example.noci.R
import com.example.noci.ThemeKey
import com.example.noci.databinding.FragmentInputBinding
import com.example.noci.notes.MODE_ENABLER
import com.example.noci.setThemeKey
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

        // the problem is here
        if(ThemeKey.theme == "dark_mode") {
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            //Hawk.put(MODE_ENABLER, "dark_mode")
            setThemeKey("dark_mode")
        } else if(ThemeKey.theme == "light_mode") {
            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            //Hawk.put(MODE_ENABLER, "light_mode")
            setThemeKey("light_mode")
        }

//        Hawk.init(context).build()
//
//        currentNightMode = AppCompatDelegate.getDefaultNightMode()
//        val theme = Hawk.get<String>(MODE_ENABLER, "light_mode")
//
//        // the problem is here
//        if(theme == "dark_mode") {
//            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            Hawk.put(MODE_ENABLER, "dark_mode")
//        } else if(theme == "light_mode") {
//            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            Hawk.put(MODE_ENABLER, "light_mode")
//        }

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
                Toast.makeText(context, "Title field can't be empty!", Toast.LENGTH_SHORT)
                    .show()
                binding.addTitle.requestFocus()
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

    fun noteOpacityChanger(typeNumber: Int) {
        if(typeNumber == 0) {
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if(typeNumber == 1) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if(typeNumber == 2) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if(typeNumber == 3) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if(typeNumber == 4) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFiveImage.alpha = 1F
        } else if(typeNumber == 5) {
            binding.qualityZeroImage.alpha = 1F
            binding.qualityOneImage.alpha = 1F
            binding.qualityTwoImage.alpha = 1F
            binding.qualityThreeImage.alpha = 1F
            binding.qualityFourImage.alpha = 1F
        }
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