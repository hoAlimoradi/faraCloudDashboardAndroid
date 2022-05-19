package net.faracloud.dashboard.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sensor_observation_table")
data class SensorObservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    var sensorId: String?,
    val time: Long,
    var timestamp: Long?,
    val value: Long,
    var lastUpdateDevice: Long?
)
