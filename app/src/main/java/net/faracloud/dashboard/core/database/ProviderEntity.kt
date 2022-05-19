package net.faracloud.dashboard.core.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "provider_table")
data class ProviderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val authorizationToken: String?,
    var createDate: Long?,
    var enable: Boolean,
    var provider: String?,
    var lastUpdateDevice: Long?
)


