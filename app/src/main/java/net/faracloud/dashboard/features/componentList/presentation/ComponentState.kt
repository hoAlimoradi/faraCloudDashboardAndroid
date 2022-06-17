package net.faracloud.dashboard.features.componentList.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class ComponentState: BuilderViewState {
    IDLE,
    LOADING,
    EMPTY,
    RETRY,
    START_SENSOR_LIST
}
