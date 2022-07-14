package net.faracloud.dashboard.features.sensorDetails.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.database.SensorObservationEntity
import net.faracloud.dashboard.core.database.relations.SensorsWithObservation
import net.faracloud.dashboard.core.model.ObservationRemoteModel
import net.faracloud.dashboard.core.model.ObservationRemoteModels
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Response

interface SensorDetailsRepository {

    fun getAllSensors(): LiveData<List<SensorEntity>>

    fun getSensor(sensor: String): LiveData<SensorEntity>

    fun getSensorsWithObservation(sensor: String): LiveData<List<SensorsWithObservation>>

    fun getLastSensorId(): String

    fun getLastProviderId(): String

    fun getLastAuthorizationToken(): String

    suspend fun getObservationsFromApi(token: String,
                                       providerId: String,
                                       sensor: String,
                                       categoryNumber: Int?,
                                       startDate: String?,
                                       endDate: String?): Response<ObservationRemoteModels>

    fun getAllObservations(): LiveData<List<SensorObservationEntity>>

    suspend fun insertObservation(component: SensorObservationEntity): Long

    suspend fun deleteAllObservations()
}