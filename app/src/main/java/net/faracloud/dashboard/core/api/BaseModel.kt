package net.faracloud.dashboard.core.api

import com.google.gson.annotations.SerializedName

data class BaseModel<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("error")
    val error: ErrorModel?
): NoObfuscate

data class ErrorModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("extra")
    val extra: Any?,
    @SerializedName("code")
    val code: Int
): NoObfuscate