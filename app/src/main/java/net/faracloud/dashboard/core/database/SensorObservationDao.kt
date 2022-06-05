package net.faracloud.dashboard.core.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SensorObservationDao {

    @Transaction
    suspend fun updateSensorObservation(entity: SensorObservationEntity) {
        entity.let {
            insertSensorObservation(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSensorObservation(entity: SensorObservationEntity): Long

    @Query("DELETE FROM sensor_observation_table")
    suspend fun deleteSensorObservations()

    @Query("SELECT * FROM sensor_observation_table ORDER BY time")
    fun getSensorObservations(): LiveData<List<SensorObservationEntity>>
}