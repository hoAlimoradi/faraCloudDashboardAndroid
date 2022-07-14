package net.faracloud.dashboard.core.sharedpreferences

interface PreferenceHelper {

    fun getTenantName(): String
    fun setTenantName(tenantName: String)

    fun getLastProviderId(): String
    fun setLastProviderId(lastProviderId: String)

    fun getLastComponentId(): String
    fun setLastComponentId(lastComponentId: String)

    fun getLastSensorId(): String
    fun setLastSensorId(lastSensorId: String)

    fun getLastAuthorizationToken(): String
    fun setLastAuthorizationToken(lastAuthorizationToken: String)

    fun setBaseUrl(baseUrl: String)
    fun getBaseUrl(): String
}