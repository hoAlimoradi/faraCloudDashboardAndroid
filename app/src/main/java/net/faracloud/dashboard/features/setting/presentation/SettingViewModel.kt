package net.faracloud.dashboard.features.setting.presentation

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import net.faracloud.dashboard.features.setting.data.SettingRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    var scheduler: SchedulersImpl,
    private val repository: SettingRepository,
    @ApplicationContext private val context: Context
) : BuilderViewModel<SettingState>(SettingState.IDLE) {
      fun getBaseUrl(): String {
        return repository.getBaseUrl()
    }

      fun setBaseUrl(baseUrl: String) {
          repository.setBaseUrl(baseUrl)
    }
}