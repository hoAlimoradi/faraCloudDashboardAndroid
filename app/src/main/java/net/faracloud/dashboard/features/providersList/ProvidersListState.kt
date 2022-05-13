package net.faracloud.dashboard.features.providersList

import net.faracloud.dashboard.core.BuilderViewState

enum class ProvidersListState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_SENSOR_LIST
}