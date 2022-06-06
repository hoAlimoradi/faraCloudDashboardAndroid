package net.faracloud.dashboard.features.splash.presentation

import net.faracloud.dashboard.core.BuilderViewState

enum class SplashState: BuilderViewState {
    IDLE,
    LOADING,
    RETRY,
    START_ON_BOARDING,
    START_ADD_PROVIDER,
    START_HOME,
    FORCE_UPDATE,
    NEW_VERSION_READY
}