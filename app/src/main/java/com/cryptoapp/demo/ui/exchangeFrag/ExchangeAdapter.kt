package com.cryptoapp.demo.ui.exchangeFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cryptoapp.demo.R
import com.cryptoapp.demo.databinding.ItemCryptoBinding
import com.cryptoapp.demo.helper.formatToTwoDecimalPlaces
import com.cryptoapp.demo.model.cryptoInfo.Data

class ExchangeAdapter(val context: Context) :
    ListAdapter<Data, ExchangeAdapter.ViewHolder>(Difference) {

    inner class ViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data) {

            Glide.with(context)
                .load(item.logo)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_loading))
                .into(binding.topIcon)

            binding.topCodeName.text = item.symbol
            binding.topName.text = item.name
            binding.topPrice.text = String.format(
                ContextCompat.getString(context, R.string.price),
                item.quote.usd.price.formatToTwoDecimalPlaces()
            )
            val volume = item.quote.usd.volumeChange24h
            binding.topGrowth.text = volume.toString()

            val growth = volume >= 0
            binding.topGrowth.setTextColor(
                ContextCompat.getColor(
                    context,
                    if (growth) R.color.green else R.color.red
                )
            )

            binding.imgGraph.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (growth)
                        R.drawable.ic_profit else R.drawable.ic_loss
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCryptoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object Difference : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
}