package net.faracloud.dashboard.features.search

import net.faracloud.dashboard.core.BuilderViewState

enum class SearchState: BuilderViewState {
    IDLE,
    LOADING,
    EMPTY
}