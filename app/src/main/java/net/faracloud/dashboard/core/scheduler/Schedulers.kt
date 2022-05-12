package net.faracloud.dashboard.core.scheduler

import kotlinx.coroutines.CoroutineDispatcher

interface Schedulers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val ui: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}