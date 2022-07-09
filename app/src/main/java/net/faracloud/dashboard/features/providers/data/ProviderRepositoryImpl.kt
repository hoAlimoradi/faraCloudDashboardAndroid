package net.faracloud.dashboard.features.providers.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.database.*
import net.faracloud.dashboard.core.database.doa.ProviderDao
import net.faracloud.dashboard.core.database.relations.TenantWithProviders
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProviderRepositoryImpl @Inject constructor(
    private val providerService: ProviderService,
    private val providerDao: ProviderDao,
    private val tenantDao: TenantDao,
    private val pref: PreferenceHelper
)
    : ProviderRepository {
    override fun getAllTenants(): LiveData<List<TenantEntity>> = tenantDao.getTenants()
    override fun getTenantWithProviders(tenantName: String): LiveData<List<TenantWithProviders>>  = tenantDao.getTenantWithProviders(tenantName)

    //override fun getTenantWithProviders(tenantName: String)  =  tenantDao.getTenantWithProviders(tenantName)
    override fun getAllProviders(): LiveData<List<ProviderEntity>> =  providerDao.getProviders()

    override fun getProviderByProviderId(providerId: String) = providerDao.getProviderByProviderId(providerId)

   /* override fun getProviderById(id: String): LiveData<ProviderEntity> {
       return providerDao.getProviderById(id)
    }*/

    override suspend fun insertProvider(provider: ProviderEntity) = providerDao.insertProvider(provider)

    override suspend fun updateProvider(provider: ProviderEntity) {
        providerDao.updateProvider(provider)
    }

    override suspend fun deleteProvider(provider: ProviderEntity) = providerDao.delete(provider)

    override suspend fun deleteAllProviders() = providerDao.deleteProviders()
    
    override fun getLastTenantName(): String {
        return pref.getTenantName()
    }

    override fun setLastTenantName(tenantName: String) {
        return pref.setTenantName(tenantName)
    }

    override fun getLastProviderId(): String {
        return pref.getLastSensorId()
    }

    override fun setLastProviderId(lastProviderId: String) {
        return pref.setLastProviderId(lastProviderId)
    }

    override fun getLastAuthorizationToken(): String {
        return pref.getLastAuthorizationToken()
    }

    override fun setLastAuthorizationToken(lastAuthorizationToken: String) {
        pref.setLastAuthorizationToken(lastAuthorizationToken)
    }

}
