package net.faracloud.dashboard.features.setting.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(): SettingRepository {
    override fun getBaseUrl(): String {
        return ""
    }

    override fun setBaseUrl(baseUrl: String) {
        print(baseUrl)
    }
}