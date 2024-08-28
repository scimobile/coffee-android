package com.sci.coffeeandroid.feature.auth.data.datasource

import com.tencent.mmkv.MMKV

class TokenService {

    private val KEY_ACCESS_TOKEN = "access_token"
    var token: String?
        get() = MMKV.defaultMMKV().decodeString(KEY_ACCESS_TOKEN, null)
        set(value) {
            MMKV.defaultMMKV().encode(KEY_ACCESS_TOKEN, value)
        }
}