package net.faracloud.dashboard.features.sensorsList.presentation


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.api.NetworkUtil
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

    fun getSensorsFromDataBase() = repository.getAllSensors()

    suspend fun getSensors(providerId: String){
        val providerId1 = "mobile-app@p101"
        val authorizationToken1= "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df"
        val providerId = "mobile-app@p102"
        val authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb"

        state.value = SensorsListState.LOADING
        //statistics.postValue(Resource.Loading())
        try{

            if(NetworkUtil.hasInternetConnection(context)){
                val response = repository.getSensorsFromApi(providerId = providerId,
                token = authorizationToken)
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        // TODO saveto database
                        loge(resultResponse.toString())
                        sensorRecycleViewViewRowEntityListMutableLiveData.value = mapperRemoteModelProviderToProviderRecycleViewViewRowEntityLsit(resultResponse)

                    }
                }
                state.value = SensorsListState.IDLE
                //statistics.postValue(handleBreakingNewsResponse(response))
            }
            else{
                state.value = SensorsListState.RETRY
                //statistics.postValue(Resource.Error("No Internet Connection"))
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

   private fun createMockProviders(): List<ProviderRecycleViewViewRowEntity> {

        var mockProviders: MutableList<ProviderRecycleViewViewRowEntity> = mutableListOf()

        val providerModel1 = ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p101",
            authorizationToken = "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df",

            enable = true)

        val providerModel2 = ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p102",
            authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb",

            enable = false)

        val providerModel3 =  ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p103",
            authorizationToken = "c5cc1e52cdd276f852c0ad09d3a2b835189383208791acba14a459dbec99c144",

            enable = false)

        val providerModel4 =  ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p104",
            authorizationToken = "9bb042b32d7c68aa620a2f7db605ecd35a275f6a896dace131c1fb77f9a82599",

            enable = false)

        mockProviders.add(providerModel1)
        mockProviders.add(providerModel2)
        mockProviders.add(providerModel3)
        mockProviders.add(providerModel4)

        return mockProviders
    }
}
