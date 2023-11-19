package com.cryptoapp.demo.ui.exchangeFrag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoapp.demo.helper.CONSTANTS
import com.cryptoapp.demo.helper.logthis
import com.cryptoapp.demo.repository.network.CryptoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val cryptoApi: CryptoApi,
    @Named(CONSTANTS.API_KEY) private val apiKey: String
) : ViewModel() {

    init {
        getCrypto()
    }

    fun getCrypto() {
        logthis("Calling api $apiKey")
        viewModelScope.launch {
            val data = cryptoApi.getCrypto(limit = 20, key = apiKey)
        }
    }
}