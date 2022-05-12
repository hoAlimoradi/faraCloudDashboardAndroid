package net.faracloud.dashboard.core.api

import com.google.gson.annotations.SerializedName

data class AccessTokenRemoteModel(
    @SerializedName("access")
    val accessToken: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("id")
    val id: String?
): NoObfuscate