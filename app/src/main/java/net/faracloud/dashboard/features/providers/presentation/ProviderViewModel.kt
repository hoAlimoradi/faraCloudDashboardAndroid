package net.faracloud.dashboard.features.providers.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.providers.data.ProviderRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProviderViewModel @Inject constructor(
    var scheduler: SchedulersImpl,
    private val repository: ProviderRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<ProviderState>(ProviderState.IDLE) {

    sealed class SavedProviderEvent {
        data class ShowUndoDeleteArticleMessage(val article: ProviderEntity): SavedProviderEvent()
    }
    private val savedProviderEventChannel = Channel<SavedProviderEvent>()
    val savedProviderEvent = savedProviderEventChannel.receiveAsFlow()

    fun deleteProvider() {
        viewModelScope.launch {
            //delay(1000)
            state.value = ProviderState.START_COMPONENT_LIST
        }
    }

    fun navigateToEditProvider() {
        state.value = ProviderState.EDIT_COMPONENT
        viewModelScope.launch {
            //delay(1000)

        }
    }

    fun navigateToSensorsOfProvider() {
        loge("navigateToSensorsOfProvider in view model")
        state.value = ProviderState.START_COMPONENT_LIST
        /*viewModelScope.launch {
            //delay(1000)
            state.value = ProviderState.START_COMPONENT_LIST
        }*/
    }

    fun getTenants() = repository.getAllTenants()

    fun getTenantWithProviders(tenantName: String) =  repository.getTenantWithProviders(tenantName)

    fun getProviders() = repository.getAllProviders()

    fun insertProvider(tenant: String,name: String, token: String) {
        viewModelScope.launch {
            repository.insertProvider(
                ProviderEntity(
                    providerId = name,
                    tenantName = tenant,
                    authorizationToken = token,
                    enable = false,
                    createDate = Date().toString(),
                    lastUpdateDevice = Date().toString()
                )
            )
        }
    }

    fun getProviderByProviderId(name: String): LiveData<ProviderEntity> = repository.getProviderByProviderId(name)


    fun updateProvider(provider: ProviderEntity) {
        viewModelScope.launch {
            repository.updateProvider(provider)
        }
    }

    fun deleteProvider(provider: ProviderEntity) {
        viewModelScope.launch {
            repository.deleteProvider(provider)

        }
    }



}

/*

    private fun createMockStatistics(): List<StatisticsRecycleViewViewRowEntity> {

        var mockStatistics: MutableList<StatisticsRecycleViewViewRowEntity> = mutableListOf()

        val statisticModel1 = StatisticsRecycleViewViewRowEntity(
            title = "Devices",
            titleCounter = "8",
            subTitle = "Active sensors",
            description = "Routers / Gateways",
            descriptionCounter = "0 ",
            secondDescription = "Other",
            secondDescriptionCounter = "8"
        )

        val statisticModel2 = StatisticsRecycleViewViewRowEntity(
            title = "Requests",
            titleCounter = "1",
            subTitle = "Requests processed",
            description = "Orders",
            descriptionCounter = "0 ",
            secondDescription = "Alarms",
            secondDescriptionCounter = "0"
        )

        val statisticModel3 = StatisticsRecycleViewViewRowEntity(
            title = "Performance",
            titleCounter = "0",
            subTitle = "Requests per second",
            description = "Max. daily average",
            descriptionCounter = "0 ",
            secondDescription = "Max. average",
            secondDescriptionCounter = "0"
        )

        val statisticModel4 = StatisticsRecycleViewViewRowEntity(
            title = "Accounts",
            titleCounter = "1",
            subTitle = "Active users",
            description = "Providers",
            descriptionCounter = "4 ",
            secondDescription = "Applications",
            secondDescriptionCounter = "0"
        )

        mockStatistics.add(statisticModel1)
        mockStatistics.add(statisticModel2)
        /* mockStatistics.add(statisticModel3)
         mockStatistics.add(statisticModel4)
 */
        return mockStatistics
    }


    private fun createMockProviders(): List<ProviderRecycleViewViewRowEntity> {

        var mockProviders: MutableList<ProviderRecycleViewViewRowEntity> = mutableListOf()

        val providerModel1 = ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p101",
            authorizationToken = "1036124625b0f33d8057b7092a27e2ba3f9925e57e7a412f6e62253f9e63b8df",

            enable = true
        )

        val providerModel2 = ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p102",
            authorizationToken = "f1f92ad3d7488f3e7cd6a6552e26717fc5232e1ae9726d5cd16d1cbd8597cfdb",

            enable = false
        )

        val providerModel3 = ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p103",
            authorizationToken = "c5cc1e52cdd276f852c0ad09d3a2b835189383208791acba14a459dbec99c144",

            enable = false
        )

        val providerModel4 = ProviderRecycleViewViewRowEntity(
            title = "mobile-app@p104",
            authorizationToken = "9bb042b32d7c68aa620a2f7db605ecd35a275f6a896dace131c1fb77f9a82599",

            enable = false
        )

        mockProviders.add(providerModel1)
        /*   mockProviders.add(providerModel2)
           mockProviders.add(providerModel3)
           mockProviders.add(providerModel4)*/

        return mockProviders
    }
 */