package net.faracloud.dashboard.features.map.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.doa.ComponentDao
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.doa.ProviderDao
import net.faracloud.dashboard.features.map.domain.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val providerDao: ProviderDao,
    private val componentDao: ComponentDao
) : MapRepository {
    var providerId = "mobile-app@p101"
    var authorizationToken = "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df"


    override fun getAllComponents(): LiveData<List<ComponentEntity>> = componentDao.getComponents()

}