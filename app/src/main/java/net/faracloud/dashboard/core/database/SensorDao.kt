package net.faracloud.dashboard.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorDao {

    @Transaction
    suspend fun updateSensor(entity: SensorEntity) {
        entity.let {
            insertSensor(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSensor(entity: SensorEntity): Long

    @Query("DELETE FROM sensor_table")
    suspend fun deleteSensors()

    @Query("SELECT * FROM sensor_table ORDER BY id")
    fun getSensors(): LiveData<List<SensorEntity>>
}
