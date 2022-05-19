package net.faracloud.dashboard.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "sensor_table")
data class SensorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var componentId: String?,
    var createdAt: Long?,
    var dataType: String?,
    val publicAccess: Boolean,
    val latitude: Double,
    val longitude: Double,
    var sensor: String?,
    var state: String?,
    var timeZone: String?,
    var type: String?,
    var unit: String?,
    var updatedAt: Long?,
    var enable: Boolean,
    var lastUpdateDevice: Long?
)

