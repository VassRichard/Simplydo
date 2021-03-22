package com.example.currentcy.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currentcy.R
import com.example.currentcy.databinding.FragmentCurrencyBinding

class CurrencyFragment: Fragment() {

    lateinit var binding: FragmentCurrencyBinding
    lateinit var currencyViewModel: CurrencyViewModel

//    val adapter = CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        currencyViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        binding.viewModel = currencyViewModel



        return binding.root
    }

    override fun onStart() {
        super.onStart()



    }

}