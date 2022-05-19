package net.faracloud.dashboard.core.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProviderDao {

    @Transaction
    suspend fun updateProvider(entity: ProviderEntity) {
        entity.let {
            //deleteProviders() // This deletes previous locations to keep the database small. If you want to store a full location history, remove this line.
            insertProvider(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProvider(entity: ProviderEntity)

    @Query("DELETE FROM provider_table")
    suspend fun deleteProviders()

    @Query("SELECT * FROM provider_table ORDER BY id")
    fun getProviders(): Flow<List<ProviderEntity>>
}


