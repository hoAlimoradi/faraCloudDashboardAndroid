package net.faracloud.dashboard.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM provider_table ORDER BY id")
    fun getProviders(): LiveData<List<ProviderEntity>>
}


