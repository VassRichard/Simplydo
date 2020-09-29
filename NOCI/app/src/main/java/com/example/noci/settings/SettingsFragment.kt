package com.example.noci.settings

import android.content.Intent
<<<<<<< HEAD
import android.content.res.Configuration
=======
import android.graphics.*
import android.os.Build
>>>>>>> parent of 86dc984... New app name/splash/logo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
=======
import android.widget.Switch
import androidx.annotation.RequiresApi
>>>>>>> parent of 86dc984... New app name/splash/logo
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.MainActivity
import com.example.noci.R
import com.example.noci.databinding.FragmentSettingsBinding
<<<<<<< HEAD
import com.example.noci.notes.NotesViewModel
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_settings.*
=======
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*


const val SWITCH_CHECKED: String = ""
>>>>>>> parent of 86dc984... New app name/splash/logo

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding.settingsViewModel = settingsViewModel

<<<<<<< HEAD
        Hawk.init(context).build()
=======
        var theme = Hawk.get<String>(SWITCH_CHECKED)
        if(theme == "dark_mode") {
            binding.darkModeSwitch.isChecked = true
        } else {
            binding.darkModeSwitch.isChecked = false
        }
>>>>>>> parent of 86dc984... New app name/splash/logo

        return binding.root
    }

<<<<<<< HEAD
    override fun onStart() {
        super.onStart()

//        if(Hawk.contains("light_mode")) {
//            dark_mode_switch.isChecked = true
//        } else {
//            dark_mode_switch.isChecked = false
//        }

        settingsViewModel.onClickedSwitch.observe(viewLifecycleOwner, Observer {
            //dark_mode_switch.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
            //if(it == true)
            //{
                activity?.setTheme(R.style.AppThemeDark)
                //activity?.startActivity(Intent(context, MainActivity::class.java))
                //activity?.finish()
                Hawk.put(SWITCH_CHECKED, "dark_mode")
            //}

                    //Hawk.put(SWITCH_CHECKED, "dark_mode")
//                } else {
//                    activity?.setTheme(R.style.AppTheme)
//                    startActivity(Intent(context, MainActivity::class.java))
//                    activity?.finish()
//                    Hawk.put(SWITCH_CHECKED, "light_mode")
//                }
            //}
//            if(it == true) {
//                activity?.setTheme(R.style.AppThemeDark)
//                startActivity(Intent(context, MainActivity::class.java))
//                activity?.finish()
//            } else {
//                activity?.setTheme(R.style.AppTheme)
//                startActivity(Intent(context, MainActivity::class.java))
//                activity?.finish()
//            }
//
//            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//                binding.darkModeSwitch.isChecked
//            }

            //binding.darkModeSwitch.setOnCheckedChangeListener(new Compound)
        })
    }
=======
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStart() {
        super.onStart()

        binding.darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                //activity?.setTheme(R.style.AppThemeDark)
                //activity?.startActivity(Intent(context, MainActivity::class.java))
                //activity?.finish()
                Hawk.put(SWITCH_CHECKED, "dark_mode")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                //activity?.setTheme(R.style.AppTheme)
                //startActivity(Intent(context, MainActivity::class.java))
                //activity?.finish()
                Hawk.put(SWITCH_CHECKED, "light_mode")
            }
        }
    }

>>>>>>> parent of 86dc984... New app name/splash/logo
}