package net.faracloud.dashboard.core.sharedpreferences

import kotlinx.coroutines.flow.Flow


interface PreferenceHelper {

    fun getTenantName(): String
    fun setTenantName(tenantName: String)

    fun getLastProviderId(): String
    fun setLastProviderId(lastProviderId: String)

    fun getLastAuthorizationToken(): String
    fun setLastAuthorizationToken(lastAuthorizationToken: String)

    fun getLastSensorId(): String
    fun setLastSensorId(lastSensorId: String)

    fun setBaseUrl(baseUrl: String)
    fun getBaseUrl(): String
}