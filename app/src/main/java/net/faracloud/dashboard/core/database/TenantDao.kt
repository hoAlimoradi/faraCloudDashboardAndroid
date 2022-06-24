package net.faracloud.dashboard.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.database.relations.TenantWithProviders

@Dao
interface TenantDao {

    @Transaction
    suspend fun updateTenant(entity: TenantEntity) {
        entity.let {
            insertTenant(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTenant(entity: TenantEntity): Long

    @Delete
    suspend fun deleteTenant(tenant: TenantEntity)

    @Query("SELECT * FROM tenant_table ORDER BY tenantName")
    fun getTenants(): LiveData<List<TenantEntity>>

    @Transaction
    @Query("SELECT * FROM tenant_table WHERE tenantName = :tenantName")
    fun getTenantWithProviders(tenantName: String): LiveData<List<TenantWithProviders>>
}
