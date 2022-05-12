package net.faracloud.dashboard.core.scheduler

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SchedulersImpl @Inject constructor() : Schedulers {

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Main

    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
    /*override fun newThread(name: String): CoroutineDispatcher {
        TODO("Not yet implemented")
    }*/
}