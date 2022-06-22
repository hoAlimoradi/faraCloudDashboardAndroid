package net.faracloud.dashboard.core.database.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import net.faracloud.dashboard.core.database.ProviderEntity

@Dao
interface ProviderDao {

    @Transaction
    suspend fun updateProvider(provider: ProviderEntity) {
        provider.let {
            //deleteProviders() // This deletes previous locations to keep the database small. If you want to store a full location history, remove this line.
            insertProvider(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProvider(provider: ProviderEntity): Long

    @Delete
    suspend fun delete(provider: ProviderEntity)

    @Query("DELETE FROM provider_table")
    suspend fun deleteProviders()

    @Query("SELECT * FROM provider_table ORDER BY providerId")
    fun getProviders(): LiveData<List<ProviderEntity>>

    @Query("SELECT * FROM provider_table WHERE providerId = :providerId")
    fun getProviderByProviderId(providerId: String): LiveData<ProviderEntity>


    @Query("SELECT * FROM provider_table WHERE providerId = :id")
    fun getProviderById(id: String): LiveData<ProviderEntity>
}


