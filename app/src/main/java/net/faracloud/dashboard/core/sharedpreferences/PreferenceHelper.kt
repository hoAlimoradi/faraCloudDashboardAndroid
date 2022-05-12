package net.faracloud.dashboard.core.sharedpreferences

import kotlinx.coroutines.flow.Flow


interface PreferenceHelper {

    fun getAccessToken(): String

    fun isLogin(): Boolean

    fun getRefreshToken(): String

    fun setAccessToken(accessToken: String)

    fun setRefreshToken(token: String)

    fun getMobile(): Flow<String>

    fun setMobile(mobile: String)

    fun clearToken(): Boolean
}