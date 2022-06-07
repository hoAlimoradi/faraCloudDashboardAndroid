package net.faracloud.dashboard.features.search.data

import net.faracloud.dashboard.core.database.ComponentDao
import net.faracloud.dashboard.core.database.ProviderDao
import net.faracloud.dashboard.core.database.SensorDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositoryImpl @Inject constructor(
    private val providerDao: ProviderDao,
    private val sensorDao: SensorDao,
    private val componentDao: ComponentDao,
): SearchRepository {

}