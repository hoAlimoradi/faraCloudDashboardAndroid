package net.faracloud.dashboard.core.useCase

import kotlinx.coroutines.flow.Flow

abstract class BaseObserveUseCase<ReturnType> {
    abstract suspend fun observe(): Flow<ReturnType>
}
