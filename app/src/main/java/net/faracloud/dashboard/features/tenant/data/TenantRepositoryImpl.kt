package net.faracloud.dashboard.features.tenant.data

import net.faracloud.dashboard.core.database.*
import net.faracloud.dashboard.features.sensorsList.data.SensorRepository

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.model.RemoteModelProvider
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TenantRepositoryImpl @Inject constructor(
    private val tenantDao: TenantDao
): TenantRepository {
    override fun getAllTenants(): LiveData<List<TenantEntity>> = tenantDao.getTenants()

    override suspend fun insertTenant(tenant: TenantEntity)  = tenantDao.insertTenant(tenant)

    override suspend fun deleteTenant(tenant: TenantEntity)  = tenantDao.deleteTenant(tenant)
}