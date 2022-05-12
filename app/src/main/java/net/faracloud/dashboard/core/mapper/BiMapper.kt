package net.faracloud.dashboard.core.mapper

interface BiMapper<T, R> {

    fun mapTo(item: T): R

    fun mapBack(item: R): T
}