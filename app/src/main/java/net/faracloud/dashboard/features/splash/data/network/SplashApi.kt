package net.faracloud.dashboard.features.splash.data.network

import net.faracloud.dashboard.core.api.BaseModel
import retrofit2.http.GET
import net.faracloud.dashboard.core.api.apiresponse.ApiResponse


interface SplashApi {
    @GET("/")
    suspend fun getConfig(): ApiResponse<BaseModel<String>>
}