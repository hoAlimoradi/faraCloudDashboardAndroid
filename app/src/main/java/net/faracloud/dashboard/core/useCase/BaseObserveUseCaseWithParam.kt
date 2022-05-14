package net.faracloud.dashboard.core.useCase

import kotlinx.coroutines.flow.Flow

abstract class BaseObserveUseCaseWithParam <ReturnType, Params> {
    abstract suspend fun observe(params: Params): Flow<ReturnType>
}