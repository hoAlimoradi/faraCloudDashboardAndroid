package net.faracloud.dashboard.features.providers.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class ProviderState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_SENSOR_LIST
}