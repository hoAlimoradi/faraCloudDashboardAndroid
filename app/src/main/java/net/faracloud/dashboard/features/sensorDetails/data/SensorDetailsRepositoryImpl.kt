package net.faracloud.dashboard.features.sensorDetails.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.api.ObservationService
import net.faracloud.dashboard.core.database.doa.SensorDao
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.doa.SensorObservationDao
import net.faracloud.dashboard.core.database.SensorObservationEntity
import net.faracloud.dashboard.core.database.relations.SensorsWithObservation
import net.faracloud.dashboard.core.model.ObservationRemoteModels
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import retrofit2.Response
import javax.inject.Inject

class SensorDetailsRepositoryImpl @Inject constructor(
    private val observationService: ObservationService,
    private val sensorObservationDao: SensorObservationDao,
    private val sensorDao: SensorDao,
    private val pref: PreferenceHelper
): SensorDetailsRepository {

    override fun getAllSensors(): LiveData<List<SensorEntity>> = sensorDao.getSensors()

    override fun getSensor(sensor: String): LiveData<SensorEntity> {
        return sensorDao.getSensor(sensor)
    }

    override fun getSensorsWithObservation(sensor: String): LiveData<List<SensorsWithObservation>> {
        return sensorDao.getSensorsWithObservation(sensor)
    }

    override fun getLastSensorId(): String {
        return pref.getLastSensorId()
    }

    override fun getLastProviderId(): String {
        return pref.getLastProviderId()
    }

    override fun getLastAuthorizationToken(): String {
        return pref.getLastAuthorizationToken()
    }

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