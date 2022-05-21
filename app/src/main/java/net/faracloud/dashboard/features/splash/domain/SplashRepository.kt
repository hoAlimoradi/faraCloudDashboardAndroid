package net.faracloud.dashboard.features.splash.domain

import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.api.Resource

interface SplashRepository {
    suspend fun getIsFirstTimeAppIsLaunchedValue(): Flow<Boolean>
    suspend fun getVersionCode(): Flow<String>
}

