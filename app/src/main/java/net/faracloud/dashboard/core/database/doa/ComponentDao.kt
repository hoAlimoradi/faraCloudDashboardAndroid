package net.faracloud.dashboard.core.database.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.relations.ProvidersWithComponent
import net.faracloud.dashboard.core.database.relations.TenantWithProviders

@Dao
interface ComponentDao {

    @Transaction
    suspend fun updateComponent(componentEntity: ComponentEntity) {
        componentEntity.let {
            insertComponent(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponent(component: ComponentEntity): Long

    @Delete
    suspend fun delete(component: ComponentEntity)

    @Query("DELETE FROM component_table")
    suspend fun deleteComponents()

    @Query("SELECT * FROM component_table ORDER BY nameComponent")
    fun getComponents(): LiveData<List<ComponentEntity>>

/*    @Transaction
    @Query("SELECT * FROM component_table WHERE providerId = :providerId")
    fun getProviderWithComponents(providerId: String): LiveData<List<ProvidersWithComponent>>*/
}