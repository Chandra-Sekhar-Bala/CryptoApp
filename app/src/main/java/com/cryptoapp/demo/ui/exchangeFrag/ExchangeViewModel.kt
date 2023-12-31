package com.cryptoapp.demo.ui.exchangeFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoapp.demo.helper.CONSTANTS
import com.cryptoapp.demo.helper.toUnixTime
import com.cryptoapp.demo.model.SortBy
import com.cryptoapp.demo.model.cryptoInfo.Data
import com.cryptoapp.demo.model.logo.LogoCache
import com.cryptoapp.demo.repository.network.CryptoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val cryptoApi: CryptoApi,
    @Named(CONSTANTS.API_KEY) private val apiKey: String
) : ViewModel() {

    private val _cryptoData = MutableStateFlow<List<Data>>(emptyList())
    val cryptoData: StateFlow<List<Data>> get() = _cryptoData

    private val refreshIntervalMillis = 60 * 1000L
    private val logoCache = LogoCache()

    private var sortBy = SortBy.CMC_RANK

    init {
        // Start refreshing data periodically
        refreshDataPeriodically()
    }

    fun sortDataByCMCRank() {
        sortBy = SortBy.CMC_RANK
        _cryptoData.value = cryptoData.value.sortedBy { it.cmcRank }
    }

    fun sortDataByPrice() {
        sortBy = SortBy.PRICE
        _cryptoData.value = cryptoData.value.sortedBy { it.quote.usd.price }
    }

    fun sortDataByTime() {
        sortBy = SortBy.TIME
        _cryptoData.value = cryptoData.value.sortedBy { it.time.toLong() }
    }

    // api data changes at every 60s
    private fun refreshDataPeriodically() {
        viewModelScope.launch {
            while (true) {
                refreshData()
                delay(refreshIntervalMillis)
            }
        }
    }

    private suspend fun refreshData() {
        try {
            // Fetch data from the first API
            val response = cryptoApi.getCrypto(limit = 20, key = apiKey)

            // Use flow to handle each item as it becomes available
            response.data.asFlow().map { cryptoItem ->
                // Use flow to make the logo API call asynchronous
                flow {
                    val logo = logoCache.getLogo(cryptoItem.id)
                        ?: cryptoApi.getLogo(
                            apiKey,
                            cryptoItem.id
                        ).data[cryptoItem.id.toString()]?.logo
                        ?: ""

                    // Cache the logo
                    logoCache.putLogo(cryptoItem.id, logo)

                    // Emit the updated Data object
                    emit(
                        Data(
                            cryptoItem.id,
                            cryptoItem.name,
                            cryptoItem.symbol,
                            cryptoItem.time.toUnixTime().toString(),
                            cryptoItem.cmcRank,
                            cryptoItem.quote,
                            logo
                        )
                    )
                }
            }.collect { updatedData ->
                // Update UI with the updated item
                _cryptoData.value = (_cryptoData.value + updatedData.single())
                    .distinctBy { it.id }
                    .sortedBy {
                        when (sortBy) {
                            SortBy.CMC_RANK -> it.cmcRank as Comparable<Any>
                            SortBy.PRICE -> it.quote.usd.price as Comparable<Any>
                            SortBy.TIME -> it.time.toLong() as Comparable<Any>
                        }
                    }

            }

        } catch (e: Exception) {
            // Handle exceptions, display an error message, or log the error
        }
    }
}