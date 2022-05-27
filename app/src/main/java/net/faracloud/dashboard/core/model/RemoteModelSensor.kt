package net.faracloud.dashboard.core.model

import com.google.gson.annotations.SerializedName

data class RemoteModelSensor(
    @SerializedName("sensor") var sensor: String? = null,
    @SerializedName("dataType") var dataType: String? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("permission") var permission: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("unit") var unit: String? = null,
    @SerializedName("timeZone") var timeZone: String? = null,
    @SerializedName("publicAccess") var publicAccess: Boolean? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("component") var component: String? = null,
    @SerializedName("componentType") var componentType: String? = null,
    @SerializedName("componentPublicAccess") var componentPublicAccess: Boolean? = null,
    @SerializedName("technicalDetails") var technicalDetails: String? = null,
    @SerializedName("componentTechnicalDetails") var componentTechnicalDetails: String? = null,
    @SerializedName("createdAt") var createdAt: Int? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null,
    @SerializedName("componentCreatedAt") var componentCreatedAt: String? = null,
    @SerializedName("componentUpdatedAt") var componentUpdatedAt: String? = null
)


