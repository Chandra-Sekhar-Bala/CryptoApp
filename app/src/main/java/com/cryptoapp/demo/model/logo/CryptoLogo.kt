package com.cryptoapp.demo.model.logo

import com.google.gson.annotations.SerializedName

data class CryptoLogo(
    @SerializedName("data")
    val data: Map<String, CryptoItem>
) {
    data class CryptoItem(
        @SerializedName("logo")
        val logo: String
    )
}
