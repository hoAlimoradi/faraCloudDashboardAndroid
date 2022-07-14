package net.faracloud.dashboard.core.database.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.relations.ComponentsWithSensor
import net.faracloud.dashboard.core.database.relations.SensorsWithObservation

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

    @Query("SELECT * FROM sensor_table ORDER BY sensor")
    fun getSensors(): LiveData<List<SensorEntity>>

    @Transaction
    @Query("SELECT * FROM sensor_table WHERE sensor = :sensor")
    fun getSensor(sensor: String): LiveData<SensorEntity>


    @Transaction
    @Query("SELECT * FROM sensor_table WHERE sensor = :sensor")
    fun getSensorsWithObservation(sensor: String): LiveData<List<SensorsWithObservation>>



}
