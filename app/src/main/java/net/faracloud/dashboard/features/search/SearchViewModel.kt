package net.faracloud.dashboard.features.search

import net.faracloud.dashboard.features.setting.SettingState


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.database.SensorEntity
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.search.data.SearchRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    var scheduler: SchedulersImpl,
    private val repository: SearchRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<SearchState>(SearchState.IDLE) {
    val queryTextChange = MutableLiveData<String>()



    //private val sensorsLiveData: LiveData<List<SensorEntity>> = repository.getSensors()

    val sensorsMutableLiveData = MutableLiveData<List<SensorEntity>>()
    val componentsMutableLiveData = MutableLiveData<List<ComponentEntity>>()
    val providersMutableLiveData = MutableLiveData<List<ProviderEntity>>()

    val mediator = MediatorLiveData<Unit>()

    init {

        sensorsMutableLiveData.value = repository.getSensors().value
        componentsMutableLiveData.value = repository.getComponents().value
        providersMutableLiveData.value = repository.getProviders().value

        /*val title = sensorsMutableLiveData.combineWith(user) { profile, user ->
            "${profile.job} ${user.name}"
        }*/

    }

    fun getSensors(): LiveData<List<SensorEntity>> {
        viewModelScope.launch {

        }
        return repository.getSensors()
    }

    fun getComponents(): LiveData<List<ComponentEntity>> {
        return repository.getComponents()
    }

    fun getProviders(): LiveData<List<ProviderEntity>> {
        return repository.getProviders()
    }

   /* val profile = MutableLiveData<ProfileData>()

    val user = MutableLiveData<CurrentUser>()

    val title = profile.combineWith(user) { profile, user ->
        "${profile.job} ${user.name}"
    }*/

    fun <T, K, R> LiveData<T>.combineWith(
        liveData: LiveData<K>,
        block: (T?, K?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = block(this.value, liveData.value)
        }
        result.addSource(liveData) {
            result.value = block(this.value, liveData.value)
        }
        return result
    }
}