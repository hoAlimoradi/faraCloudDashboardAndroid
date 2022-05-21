package net.faracloud.dashboard.core.api

import com.google.gson.annotations.SerializedName


data class RemoteModelStats(
    @SerializedName("totalDevices") var totalDevices: String? = null,
    @SerializedName("totalRouterDevices") var totalRouterDevices: String? = null,
    @SerializedName("totalOtherDevices") var totalOtherDevices: String? = null,
    @SerializedName("totalEvents") var totalEvents: String? = null,
    @SerializedName("totalObsEvents") var totalObsEvents: String? = null,
    @SerializedName("totalOrderEvents") var totalOrderEvents: String? = null,
    @SerializedName("totalAlarmEvents") var totalAlarmEvents: String? = null,
    @SerializedName("eventsPerSecond") var eventsPerSecond: String? = null,
    @SerializedName("dailyAverageRate") var dailyAverageRate: String? = null,
    @SerializedName("maxRate") var maxRate: String? = null,
    @SerializedName("totalActiveAccounts") var totalActiveAccounts: String? = null,
    @SerializedName("totalProviderAccounts") var totalProviderAccounts: String? = null,
    @SerializedName("totalApplicationAccounts") var totalApplicationAccounts: String? = null

)
