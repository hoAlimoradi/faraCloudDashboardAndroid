package net.faracloud.dashboard.core.model

import com.google.gson.annotations.SerializedName

data class RemoteModelProvider(
    @SerializedName("provider") var provider: String? = null,
    @SerializedName("permission") var permission: String? = null,
    @SerializedName("sensors") var sensors: List<RemoteModelSensor>? = null)

