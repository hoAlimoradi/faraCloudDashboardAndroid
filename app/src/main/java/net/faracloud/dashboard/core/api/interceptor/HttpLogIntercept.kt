package net.faracloud.dashboard.core.api.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class HttpLogIntercept @Inject constructor() {
    fun getIntercept() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}