package net.faracloud.dashboard.features.sensorsList


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.providersList.ProviderRecycleViewViewRowEntity
import net.faracloud.dashboard.features.providersList.ProvidersListState
import javax.inject.Inject

@HiltViewModel
class SensorsListViewModel @Inject constructor(
    var schedulers: SchedulersImpl
) : BuilderViewModel<SensorsListState>(SensorsListState.IDLE) {

    val sensorRecycleViewViewRowEntityListMutableLiveData = MutableStateFlow<List<ProviderRecycleViewViewRowEntity>?>(null)

    fun navigateToSensorsOfProvider() {
        viewModelScope.launch {
            //delay(1000)
            state.value = SensorsListState.START_SENSOR_DETAILS
        }
    }

    fun getSensors() {
        viewModelScope.launch {
            sensorRecycleViewViewRowEntityListMutableLiveData.value = createMockProviders()
        }

    }
    private fun createMockProviders(): List<ProviderRecycleViewViewRowEntity> {

        var mockProviders: MutableList<ProviderRecycleViewViewRowEntity> = mutableListOf()

        val providerModel1 = ProviderRecycleViewViewRowEntity( identifier = "1",
            title = "mobile-app@p101",
            authorizationToken = "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df",
            description = "",
            creationDate = "18/03/2022 06:43:45",
            updatedDate =  "18/03/2022 06:43:45",
            contact = "sayyidmohsen",
            contactEmail = "sayyidmohsen@chmail.ir",
            enable = true)

        val providerModel2 = ProviderRecycleViewViewRowEntity(identifier = "2",
            title = "mobile-app@p102",
            authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb",
            description = "",
            creationDate = "18/03/2022 06:44:04",
            updatedDate = "18/03/2022 06:44:04",
            contact = "sayyidmohsen",
            contactEmail = "sayyidmohsen@chmail.ir",
            enable = false)

        val providerModel3 =  ProviderRecycleViewViewRowEntity(identifier = "3",
            title = "mobile-app@p103",
            authorizationToken = "c5cc1e52cdd276f852c0ad09d3a2b835189383208791acba14a459dbec99c144",
            description = "",
            creationDate = "18/03/2022 06:44:25",
            updatedDate = "18/03/2022 06:44:25",
            contact = "sayyidmohsen",
            contactEmail = "sayyidmohsen@chmail.ir",
            enable = false)

        val providerModel4 =  ProviderRecycleViewViewRowEntity(identifier = "4",
            title = "mobile-app@p104",
            authorizationToken = "9bb042b32d7c68aa620a2f7db605ecd35a275f6a896dace131c1fb77f9a82599",
            description = "",
            creationDate = "18/03/2022 06:44:40",
            updatedDate = "18/03/2022 06:44:40",
            contact = "sayyidmohsen",
            contactEmail = "sayyidmohsen@chmail.ir",
            enable = false)

        mockProviders.add(providerModel1)
        mockProviders.add(providerModel2)
        mockProviders.add(providerModel3)
        mockProviders.add(providerModel4)

        return mockProviders
    }
}

/*
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.splash.presentation.SplashState
import javax.inject.Inject

@HiltViewModel
class SensorsListViewModel @Inject constructor(
    var schedulers: SchedulersImpl
) : BuilderViewModel<SensorsListState>(SensorsListState.IDLE) {

}

 */