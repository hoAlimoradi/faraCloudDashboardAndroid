package net.faracloud.dashboard.features.map.data

data class ObservationRepoModel(
    val value: String,
    val latitude: Double,
    val longitude: Double,
    var sensorId: String?,
    val time: Long,
    var timestamp: Long?
)
