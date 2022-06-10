package net.faracloud.dashboard.features.statistics.data

import net.faracloud.dashboard.core.api.RemoteModelStats
import retrofit2.Response

interface StatisticsRepository {

    suspend fun getStats(name: String,
                         token: String): Response<RemoteModelStats>
}