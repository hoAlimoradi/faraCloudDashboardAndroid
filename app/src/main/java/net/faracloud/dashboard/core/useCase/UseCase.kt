package net.faracloud.dashboard.core.useCase

import net.faracloud.dashboard.core.api.Resource


interface UseCase<T,R>{
    suspend fun action(param : T) : Resource<R>
}