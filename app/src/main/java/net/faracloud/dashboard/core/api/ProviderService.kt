package net.faracloud.dashboard.core.api

import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProviderService {

    @GET("{name}/stats/json")
    suspend fun getStats(
        @Path("name") name: String,
        @Header("IDENTITY_KEY") token: String
    ): Response<RemoteModelStats>


    @GET("catalog")
    suspend fun getCatalog(
        @Header("IDENTITY_KEY") token: String
    ): Response<RemoteModelProviders>

}



/* @GET("catalog/{providerId}/")
 suspend fun getCatalog(
     @Path("providerId") providerId: String,
     @Header("IDENTITY_KEY") token: String
 ): Response<RemoteModelProviders>*/