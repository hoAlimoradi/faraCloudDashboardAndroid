package net.faracloud.dashboard.features.componentList

import net.faracloud.dashboard.core.BuilderViewState

enum class ComponentState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_SENSOR_LIST
}
