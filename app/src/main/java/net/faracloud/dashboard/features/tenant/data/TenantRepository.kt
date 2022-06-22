package net.faracloud.dashboard.features.tenant.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.TenantEntity

interface TenantRepository {
    fun getAllTenants(): LiveData<List<TenantEntity>>

    suspend fun insertTenant(tenant: TenantEntity): Long

    suspend fun deleteTenant(tenant: TenantEntity)
}
