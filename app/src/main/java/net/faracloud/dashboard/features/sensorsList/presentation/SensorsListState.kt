package net.faracloud.dashboard.features.sensorsList.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class SensorsListState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_SENSOR_DETAILS
}

