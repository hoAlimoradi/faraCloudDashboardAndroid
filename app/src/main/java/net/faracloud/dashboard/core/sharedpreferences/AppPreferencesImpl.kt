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

    override fun getLastSensorId(): String {
        return mPrefs.getString(PREF_KEY_LAST_SENSOR_ID,null) ?: ""
    }

    override fun setLastSensorId(lastSensorId: String) {
        mPrefs.edit()
            .putString(PREF_KEY_LAST_SENSOR_ID, lastSensorId)
            .apply()
    }

    override fun getTenantName(): String {
        return mPrefs.getString(PREF_KEY_LAST_TENANT_ID,null) ?: ""
    }

    override fun setTenantName(tenantName: String) {
        mPrefs.edit()
            .putString(PREF_KEY_LAST_TENANT_ID, tenantName)
            .apply()
    }

    override fun getLastProviderId(): String {
        return mPrefs.getString(PREF_KEY_LAST_PROVIDER_ID,null) ?: ""
    }

    override fun setLastProviderId(lastProviderId: String) {
        mPrefs.edit()
            .putString(PREF_KEY_LAST_PROVIDER_ID, lastProviderId)
            .apply()
    }

    override fun getLastComponentId(): String {
        return mPrefs.getString(PREF_KEY_LAST_COMPONENT_ID,null) ?: ""
    }

    override fun setLastComponentId(lastComponentId: String) {
        mPrefs.edit()
            .putString(PREF_KEY_LAST_COMPONENT_ID, lastComponentId)
            .apply()
    }

    override fun getLastAuthorizationToken(): String {
        return mPrefs.getString(PREF_KEY_LAST_AUTHORIZATION,null) ?: ""
    }

    override fun setLastAuthorizationToken(lastAuthorizationToken: String) {
        mPrefs.edit()
            .putString( PREF_KEY_LAST_AUTHORIZATION, lastAuthorizationToken)
            .apply()
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
        private const val PREF_KEY_LAST_SENSOR_ID: String = "PREF_KEY_LAST_SENSOR_ID"
        private const val PREF_KEY_LAST_PROVIDER_ID: String = "PREF_KEY_LAST_PROVIDER_ID"
        private const val PREF_KEY_LAST_COMPONENT_ID: String = "PREF_KEY_LAST_COMPONENT_ID"
        private const val PREF_KEY_LAST_AUTHORIZATION: String = "PREF_KEY_LAST_AUTHORIZATION"
        private const val PREF_KEY_LAST_TENANT_ID: String = "PREF_KEY_LAST_TENANT_ID"

    }
}
