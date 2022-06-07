package net.faracloud.dashboard.features.setting.data

interface SettingRepository {
    fun getBaseUrl(): String
    fun setBaseUrl(baseUrl: String)
}