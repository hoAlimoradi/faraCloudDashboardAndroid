package net.faracloud.dashboard.core.database.relations


import net.faracloud.dashboard.core.database.SensorEntity

import androidx.room.Embedded
import androidx.room.Relation
import net.faracloud.dashboard.core.database.SensorObservationEntity

data class SensorsWithObservation(
    @Embedded val sensor: SensorEntity,
    @Relation(
        parentColumn = "sensor",
        entityColumn = "sensor"
    )
    val observations: List<SensorObservationEntity>
)