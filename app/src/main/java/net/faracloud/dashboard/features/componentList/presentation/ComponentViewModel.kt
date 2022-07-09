package net.faracloud.dashboard.features.componentList.presentation

import net.faracloud.dashboard.features.componentList.data.ComponentRepository
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.api.NetworkUtil
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.model.RemoteModelProviders
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ComponentViewModel @Inject constructor(
    var schedulers: SchedulersImpl,
    private val repository: ComponentRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<ComponentState>(ComponentState.IDLE) {
    val errorMutableLiveData =  MutableLiveData<String?>(null)

    val componentRecycleViewViewRowEntityListMutableLiveData =
        MutableLiveData<List<ProviderRecycleViewViewRowEntity>?>(null)

    fun navigateToSensorListFragment() {
        viewModelScope.launch {
            //delay(1000)
            state.value = ComponentState.START_SENSOR_LIST
        }
    }


    suspend fun getComponentsFromApi() {

        state.value = ComponentState.LOADING
        try {
            if (NetworkUtil.hasInternetConnection(context)) {
                val response = repository.getSensorsFromApi(
                    tenantName = repository.getLastTenantName(),
                    providerId = repository.getLastProviderId(),
                    token = repository.getLastAuthorizationToken()
                )

                loge(response.body().toString())
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        // TODO saveto database
                        loge(resultResponse.toString())
                        val list = mapperRemoteModelProviderToComponentList(repository.getLastProviderId(), resultResponse)
                        if (list.isEmpty()) {
                            state.value = ComponentState.EMPTY
                        } else {
                            state.value = ComponentState.IDLE
                            componentRecycleViewViewRowEntityListMutableLiveData.value = list
                        }
                    }
                } else {
                    state.value = ComponentState.RETRY
                    errorMutableLiveData.value = response.message()
                }

            } else {
                state.value = ComponentState.RETRY
                errorMutableLiveData.value = "No Internet Connection"
            }
        } catch (ex: Exception) {
            loge(ex.message)
            loge(ex.localizedMessage)
            state.value = ComponentState.RETRY
            when(ex){
                is IOException -> errorMutableLiveData.value = "Network Failure"
                else -> errorMutableLiveData.value = "Conversion Error"
            }
        }
    }

    private fun mapperRemoteModelProviderToComponentList(
        providerId: String,
        remoteModelProviders: RemoteModelProviders
    ): List<ProviderRecycleViewViewRowEntity> {
        var components: MutableList<ProviderRecycleViewViewRowEntity> = mutableListOf()
        var componentEntityList: MutableList<ComponentEntity> = mutableListOf()
        var sensorEntityList: MutableList<SensorEntity> = mutableListOf()
        //extract component, TODO: check create Date


        remoteModelProviders.providers.forEach { remoteModelProvider ->

            //extract component
            remoteModelProvider.sensors?.forEach { remoteModelSensor ->
                val sensorLocationArray = remoteModelSensor.location.split(" ").toTypedArray()
                val latitude: Double = sensorLocationArray[0].toDouble()
                val longitude: Double = sensorLocationArray[1].toDouble()
                componentEntityList.add(
                    ComponentEntity(
                        providerId = providerId,
                        nameComponent = remoteModelSensor.component,
                        type = remoteModelSensor.componentType,
                        latitude = latitude,
                        longitude = longitude,
                        publicAccess = remoteModelSensor.componentPublicAccess,
                        createAt = null,
                        updatedAt = null,
                        enable = false,
                        lastUpdateDevice = ""
                    )
                )


                sensorEntityList.add(
                    SensorEntity(
                        componentId = remoteModelSensor.component,
                        createdAt = null,
                        dataType = remoteModelSensor.dataType,
                        publicAccess = remoteModelSensor.publicAccess,
                        latitude = latitude,
                        longitude = longitude,
                        sensor = remoteModelSensor.sensor,
                        state = remoteModelSensor.state,
                        timeZone = remoteModelSensor.timeZone,
                        type = remoteModelSensor.type,
                        unit = remoteModelSensor.unit,
                        updatedAt = null,
                        enable = false,
                        lastUpdateDevice = null
                    )
                )



            }
        }

        //saveSensor(sensorEntityList)
        //saveComponent(componentEntityList)

        componentEntityList.forEach { componentEntity ->

            components.add(
                ProviderRecycleViewViewRowEntity(
                    title = componentEntity.nameComponent!!,
                    authorizationToken = "",
                    enable = componentEntity.enable
                )
            )
        }



        return components
    }

    private fun saveComponent(componentEntityList: List<ComponentEntity>) {
        viewModelScope.launch {
            repository.deleteAllComponents()
            componentEntityList.forEach {
                val result = repository.insertComponent(it)
                loge(result.toString())
            }
        }
    }

    private fun saveSensor(sensorEntityList: List<SensorEntity>) {
        viewModelScope.launch {
            repository.deleteAllSensors()
            sensorEntityList.forEach {
                val result = repository.insertSensors(it)
                loge(result.toString())
            }
        }
    }

    fun getComponentsFromDataBase() = repository.getAllComponents()


}
