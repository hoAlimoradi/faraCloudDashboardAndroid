package net.faracloud.dashboard.core.api

import retrofit2.Call
import retrofit2.http.*
import net.faracloud.dashboard.core.api.apiresponse.ApiResponse
interface AuthService {
    @FormUrlEncoded
    @POST("auth/login/")
    suspend fun login(
        @Field("username") username: String,
        @Field("code") password: String
    ): ApiResponse<BaseModel<AccessTokenRemoteModel>>



    @FormUrlEncoded
    @POST("auth/refresh_token/")
    fun refreshToken(
        @Field("token") token: String
    ): Call<BaseModel<AccessTokenRemoteModel>>
}