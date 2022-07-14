package net.faracloud.dashboard.features.sensorsList.presentation


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.api.NetworkUtil
import net.faracloud.dashboard.core.database.relations.ComponentsWithSensor
import net.faracloud.dashboard.core.model.RemoteModelProviders
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.providers.presentation.ProviderRecycleViewViewRowEntity
import net.faracloud.dashboard.features.sensorsList.data.SensorRepository
import javax.inject.Inject

@HiltViewModel
class SensorsListViewModel @Inject constructor(
    var schedulers: SchedulersImpl,
    private val repository: SensorRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<SensorsListState>(SensorsListState.IDLE) {

    val sensorRecycleViewViewRowEntityListMutableLiveData = MutableLiveData<List<ProviderRecycleViewViewRowEntity>?>(null)

    fun navigateToSensorsOfProvider() {
        viewModelScope.launch {
            //delay(1000)
            state.value = SensorsListState.START_SENSOR_DETAILS
        }
    }

    fun setLastSensorId(sensorId: String) {
        repository.setLastSensorId(sensorId)
    }

    fun getSensorsFromDataBase() = repository.getAllSensors()

    fun getComponentWithSensors(): LiveData<List<ComponentsWithSensor>> {
        return repository.getComponentWithSensors(repository.getLastComponentId())
    }

    /*
       val providerId1 = "mobile-app@p101"
       val authorizationToken1= "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df"
       val providerId = "mobile-app@p102"
       val authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb"

        */

    suspend fun getSensors() {

        state.value = SensorsListState.LOADING
        try{

            if(NetworkUtil.hasInternetConnection(context)){
                val response = repository.getSensorsFromApi(providerId = repository.getLastProviderId(),
                token = repository.getLastAuthorizationToken())
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        // TODO saveto database
                        loge(resultResponse.toString())
                        sensorRecycleViewViewRowEntityListMutableLiveData.value = mapperRemoteModelProviderToProviderRecycleViewViewRowEntityLsit(resultResponse)

                    }
                }
                state.value = SensorsListState.IDLE
            }
            else{
                state.value = SensorsListState.RETRY
            }
        }
        catch (ex : Exception){
            loge(ex.message)
            loge(ex.localizedMessage)
            state.value = SensorsListState.RETRY
            /*when(ex){
                is IOException -> statistics.postValue(Resource.Error("Network Failure"))
                else -> statistics.postValue(Resource.Error("Conversion Error"))
            }*/
        }
    }

    fun mapperRemoteModelProviderToProviderRecycleViewViewRowEntityLsit(remoteModelProviders: RemoteModelProviders) : List<ProviderRecycleViewViewRowEntity> {
        var sensors: MutableList<ProviderRecycleViewViewRowEntity> = mutableListOf()
        remoteModelProviders.providers.forEach { remoteModelProvider ->
            remoteModelProvider.sensors?.forEach {
                sensors.add(ProviderRecycleViewViewRowEntity(title =  it.sensor!!,
                    authorizationToken =  "",
                    enable = false))
            }
        }

        return sensors
    }


}
