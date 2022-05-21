package net.faracloud.dashboard.features.tenant

import net.faracloud.dashboard.core.BuilderViewState

enum class TenantState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_SENSOR_LIST
}