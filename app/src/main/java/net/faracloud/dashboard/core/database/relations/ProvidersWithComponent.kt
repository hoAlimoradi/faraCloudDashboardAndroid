package net.faracloud.dashboard.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.ProviderEntity

data class ProvidersWithComponent(
        @Embedded val provider: ProviderEntity,
        @Relation(
                parentColumn = "providerId",
                entityColumn = "providerId"
        )
        val components: List<ComponentEntity>
)