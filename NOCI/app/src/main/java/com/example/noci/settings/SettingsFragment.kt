package com.example.noci.settings

import android.content.Intent
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.noci.MainActivity
import com.example.noci.R
import com.example.noci.databinding.FragmentSettingsBinding
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*


const val SWITCH_CHECKED: String = ""

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

        var theme = Hawk.get<String>(SWITCH_CHECKED)
        if(theme == "dark_mode") {
            binding.darkModeSwitch.isChecked = true
        } else {
            binding.darkModeSwitch.isChecked = false
        }

        return binding.root
    }

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

}