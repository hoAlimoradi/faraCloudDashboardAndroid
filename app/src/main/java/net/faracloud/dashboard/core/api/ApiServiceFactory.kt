package net.faracloud.dashboard.core.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import net.faracloud.dashboard.BuildConfig
import net.faracloud.dashboard.core.api.apiresponse.ApiResponseCallAdapterFactory
import net.faracloud.dashboard.core.api.interceptor.AuthenticatorInterceptor
import net.faracloud.dashboard.core.api.interceptor.ChuckIntercept
import net.faracloud.dashboard.core.api.interceptor.HttpLogIntercept
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import javax.inject.Inject

const val TIME_OUT: Long = 60


const val BaseUrl: String = "https://iot.faracloud.ir/"
const val BaseUrlDev: String = "https://iot.faracloud.ir/"

class ApiServiceFactory @Inject constructor(
    private val chuckIntercept: ChuckIntercept,
    private val httpLogIntercept: HttpLogIntercept,
    private val preferenceHandler: PreferenceHelper,
) {

    fun <T> create(serviceClass: Class<T>): T = retrofit(true).create(serviceClass)

    private fun retrofit(isAuth: Boolean): Retrofit {

        return Retrofit.Builder()
            .baseUrl(if (BuildConfig.DEBUG) BaseUrlDev else BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .client(okHttpClientBuilder(isAuth).build())
            .build()
    }

    private fun okHttpClientBuilder(isAuth: Boolean): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLogIntercept.getIntercept())
        if (isAuth) {
            builder.authenticator(
                AuthenticatorInterceptor(
                    preferenceHandler,
                    retrofit(false).create(AuthService::class.java)
                )
            )
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckIntercept.getIntercept())
        }
        return builder
    }
}