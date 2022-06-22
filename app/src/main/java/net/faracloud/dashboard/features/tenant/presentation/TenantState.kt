package net.faracloud.dashboard.features.tenant.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class TenantState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY
}