package com.example.noci.settings

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.MainActivity
import com.example.noci.R
import com.example.noci.databinding.FragmentSettingsBinding
import com.example.noci.notes.NotesViewModel
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_settings.*

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

        Hawk.init(context).build()

        return binding.root
    }

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
}