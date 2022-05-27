package net.faracloud.dashboard.features.providers.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.ProviderEntity

interface ProviderRepository {
    fun getAllProviders(): LiveData<List<ProviderEntity>>

    suspend fun insertProvider(provider: ProviderEntity): Long

    suspend fun deleteProvider(provider: ProviderEntity)

    suspend fun deleteAllProviders()
}

