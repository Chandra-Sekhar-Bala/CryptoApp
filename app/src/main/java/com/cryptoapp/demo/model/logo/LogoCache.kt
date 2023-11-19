package com.cryptoapp.demo.model.logo

class LogoCache {

    private val cache: MutableMap<Int, String> = mutableMapOf()

    fun getLogo(id: Int): String? {
        return cache[id]
    }

    fun putLogo(id: Int, logoUrl: String) {
        cache[id] = logoUrl
    }

    fun clear() {
        cache.clear()
    }
}
