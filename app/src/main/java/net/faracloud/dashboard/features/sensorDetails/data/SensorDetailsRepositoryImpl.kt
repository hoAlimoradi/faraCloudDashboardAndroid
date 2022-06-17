package net.faracloud.dashboard.features.sensorDetails.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ObservationService
import net.faracloud.dashboard.core.database.SensorDao
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.SensorObservationDao
import net.faracloud.dashboard.core.database.SensorObservationEntity
import net.faracloud.dashboard.core.model.ObservationRemoteModels
import net.faracloud.dashboard.extentions.loge
import retrofit2.Response
import javax.inject.Inject

class SensorDetailsRepositoryImpl @Inject constructor(
    private val observationService: ObservationService,
    private val sensorObservationDao: SensorObservationDao,
    private val sensorDao: SensorDao
): SensorDetailsRepository {
    override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override suspend fun getObservationsFromApi(
        token: String,
        providerId: String,
        sensor: String,
        categoryNumber: Int?,
        startDate: String?,
        endDate: String?
    ): Response<ObservationRemoteModels> {
        return observationService.getObservations(
            token= token,
            providerId = providerId,
            sensor = sensor,
            categoryNumber = 10,
            startDate = startDate,
            endDate = endDate
        )
    }
    override fun getAllObservations(): LiveData<List<SensorObservationEntity>> = sensorObservationDao.getSensorObservations()

    override suspend fun insertObservation(sensorObservationEntity: SensorObservationEntity): Long  = sensorObservationDao.insertSensorObservation(sensorObservationEntity)

    override suspend fun deleteAllObservations() = sensorObservationDao.deleteSensorObservations()

}