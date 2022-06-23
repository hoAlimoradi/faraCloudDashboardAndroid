package net.faracloud.dashboard.features.setting.data

import net.faracloud.dashboard.core.database.TenantDao
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val preferenceHandler: PreferenceHelper
): SettingRepository {
    override fun getBaseUrl(): String {
        return preferenceHandler.getBaseUrl()
    }

    override fun setBaseUrl(baseUrl: String) {
        preferenceHandler.setBaseUrl(baseUrl)
        print(baseUrl)
    }
}