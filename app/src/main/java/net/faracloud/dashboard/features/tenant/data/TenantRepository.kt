package net.faracloud.dashboard.features.tenant.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.database.ProviderEntity
import retrofit2.Response

interface TenantRepository {
    fun getAllProviders(): LiveData<List<ProviderEntity>>

    suspend fun insertProvider(provider: ProviderEntity): Long

    suspend fun deleteProvider(provider: ProviderEntity)

    suspend fun deleteAllProviders()

    suspend fun getStats(name: String): Response<RemoteModelStats>


}