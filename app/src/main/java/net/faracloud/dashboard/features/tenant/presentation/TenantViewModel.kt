package net.faracloud.dashboard.features.tenant.presentation

import net.faracloud.dashboard.features.tenant.data.TenantRepository

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.faracloud.dashboard.core.BuilderViewModel
import net.faracloud.dashboard.core.database.TenantEntity
import net.faracloud.dashboard.core.scheduler.SchedulersImpl
import javax.inject.Inject

@HiltViewModel
class TenantViewModel @Inject constructor(
    var schedulers: SchedulersImpl,
    private val repository: TenantRepository
) : BuilderViewModel<TenantState>(TenantState.IDLE) {

    fun getTenants() = repository.getAllTenants()

    fun insertTenant(name: String) {
        viewModelScope.launch {
            repository.insertTenant(
                TenantEntity(
                    tenantName = name
                )
            )
        }
    }
}