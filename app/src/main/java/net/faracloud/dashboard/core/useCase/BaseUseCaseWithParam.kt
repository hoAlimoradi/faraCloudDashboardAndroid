package net.faracloud.dashboard.core.useCase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCaseWithParam<ReturnType, Params> {

    abstract suspend fun run(params: Params): ReturnType

    fun execute(scope: CoroutineScope,
                processContext: CoroutineContext = Dispatchers.IO,
                resultContext: CoroutineContext = Dispatchers.Main,
                onResult: (ReturnType) -> Unit, params: Params) {
        val backgroundJob = scope.async(context = processContext) { run(params) }
        scope.launch(resultContext) { onResult(backgroundJob.await()) }
    }
}
