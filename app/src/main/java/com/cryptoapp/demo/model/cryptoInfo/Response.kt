package com.cryptoapp.demo.model.cryptoInfo

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("status")
    val status: Status,

    @SerializedName("data")
    var data: List<Data> = emptyList()
)

data class Data(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("symbol")
    val symbol: String,

    @SerializedName("cmc_rank")
    val cmcRank: Int,

    @SerializedName("quote")
    val quote: Quote,

    var logo: String = ""
)

data class Status(
    @SerializedName("error_code")
    val errorCode: Int
)

data class Quote(
    @SerializedName("USD")
    val usd: Usd
)

data class Usd(
    @SerializedName("price")
    val price: Double,

    @SerializedName("volume_change_24h")
    val volumeChange24h: Double
)
