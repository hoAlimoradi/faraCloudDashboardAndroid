package net.faracloud.dashboard.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.database.TenantEntity

data class TenantWithProviders(
    @Embedded val tenant: TenantEntity,
    @Relation(
        parentColumn = "tenantName",
        entityColumn = "tenantName"
    )
    val providers: List<ProviderEntity>
)