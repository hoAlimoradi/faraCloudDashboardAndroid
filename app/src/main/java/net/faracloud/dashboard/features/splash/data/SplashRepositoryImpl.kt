package net.faracloud.dashboard.features.splash.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.faracloud.dashboard.core.database.doa.ProviderDao
import net.faracloud.dashboard.core.database.ProviderEntity
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import net.faracloud.dashboard.features.splash.domain.SplashRepository
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val context: Application,
    private val providerDao: ProviderDao,
    private val preferenceHelper: PreferenceHelper
): SplashRepository {

    override fun getAllProviders(): LiveData<List<ProviderEntity>> =  providerDao.getProviders()
    override suspend fun getIsFirstTimeAppIsLaunchedValue(): Flow<Boolean> = flow {
        emit(false)
    }

    override suspend fun getVersionCode(): Flow<String> = flow {
        emit("1")
    }
}

