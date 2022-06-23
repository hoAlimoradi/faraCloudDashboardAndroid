package net.faracloud.dashboard.core.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) :
    PreferenceHelper {
    private val mPrefs: SharedPreferences = context.getSharedPreferences("fara_cloud_Pref", Context.MODE_PRIVATE)

    override fun getAccessToken(): String {
        val token = mPrefs.getString(PREF_KEY_ACCESS_TOKEN,null) ?: ""
        return if(token.isEmpty()) "" else "Bearer $token"
    }

    override fun isLogin(): Boolean {
        return getRefreshToken().isNotEmpty()
    }

    override fun getRefreshToken(): String {
        return mPrefs.getString(PREF_KEY_REFRESH_TOKEN,null) ?: ""
    }

    override fun setAccessToken(accessToken: String) {
        mPrefs.edit()
            .putString(PREF_KEY_ACCESS_TOKEN, accessToken)
            .apply()
    }

    override fun setRefreshToken(token: String) {
        mPrefs.edit()
            .putString(PREF_KEY_REFRESH_TOKEN, token)
            .apply()
    }

    override fun getMobile(): Flow<String> = flow {
        emit(mPrefs.getString(PREF_KEY_MOBILE,null) ?: "")
    }


    override fun setMobile(mobile: String) {
        mPrefs.edit()
            .putString(PREF_KEY_MOBILE, mobile)
            .apply()
    }

    override fun clearToken() : Boolean {
        return mPrefs.edit().remove(PREF_KEY_ACCESS_TOKEN).remove(PREF_KEY_REFRESH_TOKEN).commit()
    }

    override fun setBaseUrl(baseUrl: String) {

        mPrefs.edit()
            .putString(PRE_BASE_URL, baseUrl)
            .apply()
    }

    override fun getBaseUrl(): String {
        return mPrefs.getString(PRE_BASE_URL,null) ?: "https://iotapi.faracloud.ir/"
    }

    companion object {
        private const val PRE_BASE_URL: String = "PRE_BASE_URL"
        private const val PREF_KEY_ACCESS_TOKEN: String = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_REFRESH_TOKEN: String = "PREF_KEY_REFRESH_TOKEN"
        private const val PREF_KEY_MOBILE: String = "PREF_KEY_MOBILE"
        private const val PREF_KEY_NATIONAL_CODE: String = "PREF_KEY_NATIONAL_CODE"
        private const val PREF_KEY_PASSWORD: String = "PREF_KEY_PASSWORD"
        private const val PREF_KEY_IV: String = "PREF_KEY_IV"
        private const val PREF_KEY_APP_LINK: String = "PREF_KEY_APP_LINK"
        private const val PRE_INVOICE_ID: String = "PRE_INVOICE_ID"
    }

}
