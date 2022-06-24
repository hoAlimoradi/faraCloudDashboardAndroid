package net.faracloud.dashboard.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tenant_table")
data class TenantEntity(
    @PrimaryKey(autoGenerate = false)
    var tenantName: String
)
