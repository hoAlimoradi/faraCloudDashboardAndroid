package net.faracloud.dashboard.core.api.interceptor

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import javax.inject.Inject

class ChuckIntercept @Inject constructor(
    private val application: Application
){
    fun getIntercept() = ChuckerInterceptor(application)
}