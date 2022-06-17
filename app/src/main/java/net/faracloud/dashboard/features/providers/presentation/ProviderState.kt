package net.faracloud.dashboard.features.providers.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class ProviderState: BuilderViewState {
    IDLE,
    LOADING,
    EMPTY,
    EDIT_COMPONENT,
    START_COMPONENT_LIST
}