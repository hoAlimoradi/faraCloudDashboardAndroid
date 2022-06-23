package net.faracloud.dashboard.core.di

import net.faracloud.dashboard.core.api.AuthService
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HostSelectionInterceptor @Inject constructor(
     val host: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

       // val host: String = "" //SharedPreferencesManager.getServeIpAddress()

        val newUrl = request.url.newBuilder()
            .host(host)
            .build()

        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }

}