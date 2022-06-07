package net.faracloud.dashboard.features.search

import net.faracloud.dashboard.features.setting.SettingState


import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import net.faracloud.dashboard.core.BuilderViewModel
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

}