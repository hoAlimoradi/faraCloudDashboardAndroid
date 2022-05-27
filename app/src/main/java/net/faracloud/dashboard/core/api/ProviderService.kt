package net.faracloud.dashboard.core.api

import net.faracloud.dashboard.core.api.apiresponse.ApiResponse
import net.faracloud.dashboard.core.model.ObservationRemoteModel
import net.faracloud.dashboard.core.model.RemoteModelProvider
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ProviderService {

    // //url = "https://iotapi.faracloud.ir/data/test@pp2/ps2?limit=10"
    @GET(" / ")
    suspend fun getObservations(
    ): ApiResponse<List<ObservationRemoteModel>>

/*   @Header("IDENTITY_KEY") providerId: String,  providerId: String,
    sensorId: String*/
    //@FormUrlEncoded
    @GET("{name}/stats/json")
    suspend fun getStats(
        @Path("name") name: String
    ): Response<RemoteModelStats>


    @GET("catalog")
    suspend fun getCatalog(
        @Header("IDENTITY_KEY") providerId: String
    ): Response<RemoteModelProvider>
}