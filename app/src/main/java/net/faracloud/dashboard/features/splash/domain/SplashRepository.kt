package net.faracloud.dashboard.features.splash.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.api.Resource
import net.faracloud.dashboard.core.database.ProviderEntity

interface SplashRepository {
    fun getAllProviders(): LiveData<List<ProviderEntity>>
    suspend fun getIsFirstTimeAppIsLaunchedValue(): Flow<Boolean>
    suspend fun getVersionCode(): Flow<String>
}

