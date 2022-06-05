package net.faracloud.dashboard.core.model

import com.google.gson.annotations.SerializedName

data class RemoteModelSensor(
    @SerializedName("sensor") val sensor : String,
    @SerializedName("dataType") val dataType : String,
    @SerializedName("location") val location : String,
    @SerializedName("type") val type : String,
    @SerializedName("unit") val unit : String,
    @SerializedName("timeZone") val timeZone : String,
    @SerializedName("publicAccess") val publicAccess : Boolean,
    @SerializedName("state") val state : String,
    @SerializedName("component") val component : String,
    @SerializedName("componentType") val componentType : String,
    @SerializedName("componentPublicAccess") val componentPublicAccess : Boolean,
 /*   @SerializedName("technicalDetails") val technicalDetails : TechnicalDetails,
    @SerializedName("componentTechnicalDetails") val componentTechnicalDetails : ComponentTechnicalDetails,*/
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("componentCreatedAt") val componentCreatedAt : String,
    @SerializedName("componentUpdatedAt") val componentUpdatedAt : String
)


