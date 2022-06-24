package net.faracloud.dashboard.features.statistics.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.api.NetworkUtil.Companion.hasInternetConnection
import net.faracloud.dashboard.core.api.RemoteModelStats
import net.faracloud.dashboard.core.api.Resource
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.componentList.presentation.ComponentState
import net.faracloud.dashboard.features.statistics.data.StatisticsRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    var scheduler: SchedulersImpl,
    private val repository: StatisticsRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<StatisticsState>(StatisticsState.IDLE) {

    val errorMutableLiveData =  MutableLiveData<String?>(null)

    val statisticsRecycleViewViewRowEntityListMutableLiveData =
        MutableLiveData<List<StatisticsRecycleViewViewRowEntity>?>(null)

    suspend fun getStatisticsFromApi(
        name: String,
        token: String
    ) {
        state.value = StatisticsState.LOADING
        try {

            if (hasInternetConnection(context)) {
                val response = repository.getStats(
                    name = name,
                    token = token
                )
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        // TODO saveto database
                        loge(resultResponse.dailyAverageRate)
                        var list = mapperRemoteModelStatsToStatisticsRecycleViewViewRowEntity(
                            resultResponse
                        )
                        if (list.isEmpty()) {
                            state.value = StatisticsState.EMPTY
                        } else {
                            statisticsRecycleViewViewRowEntityListMutableLiveData.value = list
                            state.value = StatisticsState.IDLE
                        }
                    }
                } else {
                    state.value = StatisticsState.RETRY
                    errorMutableLiveData.value = response.message()
                }

            } else {
                state.value = StatisticsState.RETRY
                errorMutableLiveData.value = "No Internet Connection"
            }
        } catch (ex: Exception) {
            state.value = StatisticsState.RETRY
            when(ex){
                is IOException -> errorMutableLiveData.value = "Network Failure"
                else -> errorMutableLiveData.value = "Conversion Error"
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<RemoteModelStats>): Resource<RemoteModelStats> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // TODO saveto database
                loge(resultResponse.dailyAverageRate)
                statisticsRecycleViewViewRowEntityListMutableLiveData.value =
                    mapperRemoteModelStatsToStatisticsRecycleViewViewRowEntity(resultResponse)
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun mapperRemoteModelStatsToStatisticsRecycleViewViewRowEntity(remoteModelStats: RemoteModelStats): List<StatisticsRecycleViewViewRowEntity> {
        var statistics: MutableList<StatisticsRecycleViewViewRowEntity> = mutableListOf()

        val statisticModelDevices = StatisticsRecycleViewViewRowEntity(
            title = "Devices",
            titleCounter = remoteModelStats.totalDevices.toString(),
            subTitle = "Active sensors",
            description = "Routers / Gateways",
            descriptionCounter = remoteModelStats.totalRouterDevices.toString(),
            secondDescription = "Other",
            secondDescriptionCounter = remoteModelStats.totalOtherDevices.toString()
        )

        val statisticModelRequests = StatisticsRecycleViewViewRowEntity(
            title = "Requests",
            titleCounter = remoteModelStats.dailyAverageRate.toString(),
            subTitle = "Requests processed",
            description = "Orders",
            descriptionCounter = remoteModelStats.totalRouterDevices.toString(),
            secondDescription = "Alarms",
            secondDescriptionCounter = remoteModelStats.totalAlarmEvents.toString()
        )

        val statisticModelPerformance = StatisticsRecycleViewViewRowEntity(
            title = "Performance",
            titleCounter = remoteModelStats.eventsPerSecond.toString(),
            subTitle = "Requests per second",
            description = "Max. daily average",
            descriptionCounter = remoteModelStats.dailyAverageRate.toString(),
            secondDescription = "Max. average",
            secondDescriptionCounter = remoteModelStats.dailyAverageRate.toString()
        )

        val statisticModelAccounts = StatisticsRecycleViewViewRowEntity(
            title = "Accounts",
            titleCounter = remoteModelStats.dailyAverageRate.toString(),
            subTitle = "Active users",
            description = "Providers",
            descriptionCounter = remoteModelStats.totalProviderAccounts.toString(),
            secondDescription = "Applications",
            secondDescriptionCounter = remoteModelStats.totalApplicationAccounts.toString()
        )

        statistics.add(statisticModelDevices)
        statistics.add(statisticModelRequests)
        statistics.add(statisticModelPerformance)
        statistics.add(statisticModelAccounts)

        return statistics
    }


}