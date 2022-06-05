package net.faracloud.dashboard.core.api

import net.faracloud.dashboard.core.model.ObservationRemoteModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ObservationService {
    // //url = "https://iotapi.faracloud.ir/data/test@pp2/ps2?limit=10"

    //@GET("/data/{tenant}/{providerId}?limit={categoryNumber}&from={startDate}&to={endDate}")
    @GET("/data/{providerId}/{sensor}")
    suspend fun getObservations(
        @Header("IDENTITY_KEY") token: String,
        @Path("providerId") providerId: String,
        @Path("sensor") sensor: String,
        @Query("limit") categoryNumber: Int = 10
    ): Response<ObservationRemoteModels>

  /*
   @Path("startDate") startDate: String?,
        @Path("endDate") endDate: String?
   https://api.pray.zone/v2/times/today.jsonlatitude=31.3952348&longitude=&elevation=2000&timeformat=1
    @GET("today.json")
    open fun getSalahTiming(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("elevation") elevation: Int,
        @Query("timeformat") timeformat: Int
    ): Call<SalahMainResponse?>?*/
}