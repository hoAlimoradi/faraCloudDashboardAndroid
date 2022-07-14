package net.faracloud.dashboard.core.database.relations

import net.faracloud.dashboard.core.database.SensorEntity

import androidx.room.Embedded
import androidx.room.Relation
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.ProviderEntity

data class ComponentsWithSensor(
    @Embedded val component: ComponentEntity,
    @Relation(
        parentColumn = "nameComponent",
        entityColumn = "nameComponent"
    )
    val sensors: List<SensorEntity>
)

