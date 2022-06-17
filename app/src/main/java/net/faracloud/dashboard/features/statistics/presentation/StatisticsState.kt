package net.faracloud.dashboard.features.statistics.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class StatisticsState: BuilderViewState {
    IDLE,
    LOADING,
    EMPTY,
    RETRY
}