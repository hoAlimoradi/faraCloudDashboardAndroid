package net.faracloud.dashboard.features.sensorDetails.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class SensorDetailsState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY
}
