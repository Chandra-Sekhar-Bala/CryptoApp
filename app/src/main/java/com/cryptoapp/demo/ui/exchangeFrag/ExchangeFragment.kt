package com.cryptoapp.demo.ui.exchangeFrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cryptoapp.demo.R
import com.cryptoapp.demo.databinding.FragmentExchangeBinding
import com.cryptoapp.demo.helper.logthis

class ExchangeFragment : Fragment() {

    private val binding by lazy {
        FragmentExchangeBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        logthis("Initiated!")
        activity?.findViewById<TextView>(R.id.current_frag_name)?.text = "Exchange"
        return binding.root
    }


}