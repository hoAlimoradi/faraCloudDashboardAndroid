package net.faracloud.dashboard.features.setting

import net.faracloud.dashboard.features.providers.presentation.ProviderState

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.extentions.loge
import net.faracloud.dashboard.features.providers.data.ProviderRepository
import net.faracloud.dashboard.features.setting.data.SettingRepository
import net.faracloud.dashboard.features.statistics.StatisticsRecycleViewViewRowEntity
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    var scheduler: SchedulersImpl,
    private val repository: SettingRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<SettingState>(SettingState.IDLE) {

}