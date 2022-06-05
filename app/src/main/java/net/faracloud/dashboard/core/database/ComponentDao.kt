package net.faracloud.dashboard.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComponentDao {

    @Transaction
    suspend fun updateComponent(componentEntity: ComponentEntity) {
        componentEntity.let {
           // deleteLocations() // This deletes previous locations to keep the database small. If you want to store a full location history, remove this line.
            insertComponent(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponent(component: ComponentEntity): Long

    @Delete
    suspend fun delete(component: ComponentEntity)

    @Query("DELETE FROM component_table")
    suspend fun deleteComponents()

    @Query("SELECT * FROM component_table ORDER BY id")
    fun getComponents(): LiveData<List<ComponentEntity>>
}