package net.faracloud.dashboard.features.sensorsList

import net.faracloud.dashboard.core.BuilderViewState

enum class SensorsListState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_SENSOR_DETAILS
}