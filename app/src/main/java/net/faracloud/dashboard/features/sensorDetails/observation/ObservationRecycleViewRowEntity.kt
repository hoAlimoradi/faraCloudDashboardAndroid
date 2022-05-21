package net.faracloud.dashboard.features.sensorDetails.observation

data class ObservationRecycleViewRowEntity(
    var value: String,
    var timestamp: String,
    val latitude: Double,
    val longitude: Double,
    var time: String
)
