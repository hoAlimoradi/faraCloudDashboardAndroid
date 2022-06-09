package net.faracloud.dashboard.features.search.data

import androidx.lifecycle.LiveData
import net.faracloud.dashboard.core.database.ComponentEntity
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.database.SensorEntity

interface SearchRepository {

    fun getSensors(): LiveData<List<SensorEntity>>

    fun getComponents(): LiveData<List<ComponentEntity>>

    fun getProviders(): LiveData<List<ProviderEntity>>
}