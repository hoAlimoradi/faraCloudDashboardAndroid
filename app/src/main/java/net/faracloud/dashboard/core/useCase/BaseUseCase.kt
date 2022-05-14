package net.faracloud.dashboard.core.useCase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<ReturnType> {

    abstract suspend fun run(): ReturnType

    fun execute(scope: CoroutineScope,
                processContext: CoroutineContext = Dispatchers.IO,
                resultContext: CoroutineContext = Dispatchers.Main,
                onResult: (ReturnType) -> Unit) {
        val backgroundJob = scope.async(context = processContext) { run() }
        scope.launch(resultContext) { onResult(backgroundJob.await()) }
    }
}
