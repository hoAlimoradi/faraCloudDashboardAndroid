package net.faracloud.dashboard.features.providers.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.ProviderEntity

interface ProviderRepository {
    fun getAllProviders(): LiveData<List<ProviderEntity>>

    fun getProviderByProviderId(providerId: String): LiveData<ProviderEntity>

    fun getProviderById(id: Int): LiveData<ProviderEntity>

    suspend fun insertProvider(provider: ProviderEntity): Long

    suspend fun updateProvider(provider: ProviderEntity)

    suspend fun deleteProvider(provider: ProviderEntity)

    suspend fun deleteAllProviders()


}

