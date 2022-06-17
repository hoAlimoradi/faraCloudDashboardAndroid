package net.faracloud.dashboard.core.api

import net.faracloud.dashboard.core.model.ObservationRemoteModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ObservationService {

    @GET("/data/{providerId}/{sensor}")
    suspend fun getObservations(
        @Header("IDENTITY_KEY") token: String,
        @Path("providerId") providerId: String,
        @Path("sensor") sensor: String,
        @Query("limit") categoryNumber: Int = 10,
        @Query("startDate") startDate: String?,
        @Query("to={endDate}") endDate: String?
    ): Response<ObservationRemoteModels>
}