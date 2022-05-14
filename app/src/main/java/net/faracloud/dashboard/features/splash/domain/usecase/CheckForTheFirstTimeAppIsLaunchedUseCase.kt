package net.faracloud.dashboard.features.splash.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.faracloud.dashboard.core.api.Resource
import net.faracloud.dashboard.core.useCase.BaseObserveUseCase
import net.faracloud.dashboard.core.useCase.UseCase
import net.faracloud.dashboard.features.splash.domain.SplashRepository
import javax.inject.Inject

class CheckForTheFirstTimeAppIsLaunchedUseCase @Inject constructor(
    private val splashRepository : SplashRepository
) : BaseObserveUseCase<Boolean>(){

    override suspend fun observe(): Flow<Boolean> {
        return splashRepository.getIsFirstTimeAppIsLaunchedValue()
    }
}



//class CheckForTheFirstTimeAppIsLaunchedUseCase @Inject constructor(
//    private val splashRepository : SplashRepository
//): UseCase<Unit, Boolean> {
//    override suspend fun action(param: Unit): Resource<Boolean> {
//        return splashRepository.getIsFirstTimeAppIsLaunchedValue()
//    }
//
//}