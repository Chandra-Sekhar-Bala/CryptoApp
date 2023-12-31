package com.cryptoapp.demo.ui.exchangeFrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.cryptoapp.demo.R
import com.cryptoapp.demo.databinding.FragmentExchangeBinding
import com.cryptoapp.demo.helper.formatToTwoDecimalPlaces
import com.cryptoapp.demo.helper.logthis
import com.cryptoapp.demo.model.cryptoInfo.Data
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
    var lock = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.findViewById<TextView>(R.id.current_frag_name)?.text = "Exchanges"
        binding.rcyView.adapter = adapter
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.linearLayout.setOnClickListener {
            binding.filterOption.visibility = when (binding.filterOption.visibility) {
                View.VISIBLE -> View.GONE
                View.GONE -> View.VISIBLE
                else -> View.VISIBLE
            }
        }

        binding.byCmcRank.setOnClickListener {
            hideFilter()
            viewModel.sortDataByCMCRank()
            binding.rcyView.scrollToPosition(0)
        }


        binding.byPrice.setOnClickListener {
            hideFilter()
            viewModel.sortDataByPrice()
            binding.rcyView.scrollToPosition(0)
        }


        binding.byTime.setOnClickListener {
            hideFilter()
            viewModel.sortDataByTime()
            binding.rcyView.scrollToPosition(0)
        }

        binding.homeLayout.setOnClickListener {
            hideFilter()
        }
    }

    private fun hideFilter() {
        binding.filterOption.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            viewModel.cryptoData.collect { updatedDataList ->
                // Update UI with the updated list
                logthis("Showing ....")
                try {
                    if (!lock) {
                        showFirstDataToCard(updatedDataList[0])
                        lock = !lock
                        binding.progress.visibility = View.GONE
                    }
                } catch (_: Exception) {
                }
                adapter?.submitList(updatedDataList)
            }
        }
    }

    private fun showFirstDataToCard(item: Data) {
        Glide.with(requireContext())
            .load(item.logo)
            .placeholder(R.drawable.ic_loading)
            .into(binding.topIcon)

        binding.topCodeName.text = item.symbol
        binding.topName.text = item.name
        binding.topPrice.text = String.format(
            ContextCompat.getString(requireContext(), R.string.price),
            item.quote.usd.price.formatToTwoDecimalPlaces()
        )
        val volume = item.quote.usd.volumeChange24h
        binding.topGrowth.text = volume.toString()
        binding.topGrowth.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (volume >= 0) R.color.green else R.color.red
            )
        )
    }


}