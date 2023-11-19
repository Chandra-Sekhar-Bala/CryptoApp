package com.cryptoapp.demo.ui.exchangeFrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.cryptoapp.demo.R
import com.cryptoapp.demo.databinding.FragmentExchangeBinding
import com.cryptoapp.demo.helper.logthis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExchangeFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[ExchangeViewModel::class.java] }
    private val adapter by lazy { activity?.applicationContext?.let { ExchangeAdapter(it) } }
    private val binding by lazy {
        FragmentExchangeBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.findViewById<TextView>(R.id.current_frag_name)?.text = "Exchanges"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rcyView.adapter = adapter

        lifecycleScope.launch {
            viewModel.cryptoData.collect { updatedDataList ->
                // Update UI with the updated list
                logthis("Showing ....")
                adapter?.submitList(updatedDataList)
            }
        }
    }


}