package net.faracloud.dashboard.features.statistics.data

import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import net.faracloud.dashboard.features.statistics.data.StatisticsRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticsRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val pref: PreferenceHelper
) : StatisticsRepository {
    override suspend fun getStats(
        name: String,
        token: String
    ): Response<RemoteModelStats> {
        return providerService.getStats(
            name,
            token
        )
    }

    override fun getTenantName(): String {
        return pref.getTenantName()
    }

    override fun getLastAuthorizationToken(): String {
        return pref.getLastAuthorizationToken()
    }
}
