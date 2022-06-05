package net.faracloud.dashboard.core.api

import net.faracloud.dashboard.core.api.apiresponse.ApiResponse
import net.faracloud.dashboard.core.model.ObservationRemoteModel
import net.faracloud.dashboard.core.model.ObservationRemoteModels
import net.faracloud.dashboard.core.model.RemoteModelProvider
import net.faracloud.dashboard.core.model.RemoteModelProviders
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProviderService {

/*   @Header("IDENTITY_KEY") providerId: String,  providerId: String,
    sensorId: String*/
    //@FormUrlEncoded
    @GET("{name}/stats/json")
    suspend fun getStats(
        @Path("name") name: String
    ): Response<RemoteModelStats>


    @GET("catalog/{providerId}")
    suspend fun getCatalog(
        @Path("providerId") providerId: String,
        @Header("IDENTITY_KEY") token: String
    ): Response<RemoteModelProviders>
}