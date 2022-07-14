package net.faracloud.dashboard.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "component_table")
data class ComponentEntity(
        @PrimaryKey(autoGenerate = false)
        var nameComponent: String,
        var providerId: String,
        val latitude: Double,
        val longitude: Double,
        var createAt: Long?,
        var enable: Boolean,
        var publicAccess: Boolean,
        var type: String?,
        var updatedAt: String?,
        var lastUpdateDevice: String?
)
