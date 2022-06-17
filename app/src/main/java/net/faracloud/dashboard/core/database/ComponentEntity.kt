package net.faracloud.dashboard.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "component_table")
data class ComponentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    var createAt: Long?,
    var enable: Boolean,
    var name: String?,
    var providerId: String?,
    var publicAccess: Boolean,
    var type: String?,
    var updatedAt: String?,
    var lastUpdateDevice: String?
)
