package net.faracloud.dashboard.features.map.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class MapState: BuilderViewState {
    IDLE,
    START_SETTING,
    START_DETAIL
}