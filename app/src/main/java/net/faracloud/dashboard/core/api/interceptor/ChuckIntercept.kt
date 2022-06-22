package net.faracloud.dashboard.core.api.interceptor

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class ChuckIntercept @Inject constructor(

    private val application: Application
){
    fun getIntercept() = ChuckerInterceptor(application)
}