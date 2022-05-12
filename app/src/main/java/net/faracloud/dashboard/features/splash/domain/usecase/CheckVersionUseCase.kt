/*
package net.faracloud.dashboard.features.splash.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.useCase.BaseObserveUseCase
import net.faracloud.dashboard.splash.domain.repository.SplashRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(
    private val splashRepository : SplashRepository
) : BaseObserveUseCase<String>(){
    override suspend fun observe(): Flow<String> {
        return splashRepository.getVersionCode()
    }
}*/
