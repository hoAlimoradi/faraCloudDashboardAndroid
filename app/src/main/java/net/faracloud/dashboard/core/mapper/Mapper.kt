package net.faracloud.dashboard.core.mapper

interface Mapper<T : Any?, R> {
    fun map(param: T): R
}