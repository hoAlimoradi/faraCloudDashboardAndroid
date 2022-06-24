package net.faracloud.dashboard.core.di

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.faracloud.dashboard.BuildConfig
import net.faracloud.dashboard.core.api.AuthService
import net.faracloud.dashboard.core.api.ObservationService
import net.faracloud.dashboard.core.api.ProviderService
import net.faracloud.dashboard.core.api.interceptor.AuthenticatorInterceptor
import net.faracloud.dashboard.core.api.interceptor.ChuckIntercept
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val BaseUrl: String = "https://iot.faracloud.ir/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor,
                            chuckerInterceptor: ChuckerInterceptor,
                            hostSelectionInterceptor: HostSelectionInterceptor ): OkHttpClient {
        //  OkHttpClient.Builder() getOkHttpBuilder()
        return getOkHttpBuilder()
            //.addInterceptor(hostSelectionInterceptor)
            .addInterceptor(logging)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideProviderService(retrofit: Retrofit): ProviderService {
        return retrofit.create(ProviderService::class.java)
    }

    @Provides
    @Singleton
    fun provideObservationService(retrofit: Retrofit): ObservationService {
        return retrofit.create(ObservationService::class.java)
    }

    @Singleton
    @Provides
    fun provideHostSelectionInterceptor(@ApplicationContext context: Context): HostSelectionInterceptor {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        var apiBaseUrl = BaseUrl
        sharedPrefs.getString("PRE_BASE_URL", "")?.let {
            apiBaseUrl = it
        }
        val hostSelectionInterceptor = HostSelectionInterceptor(apiBaseUrl)
        return hostSelectionInterceptor
    }


    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

        return chuckerInterceptor
    }

    private fun getOkHttpBuilder(): OkHttpClient.Builder {
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            OkHttpClient().newBuilder()
        } else {
            getUnsafeOkHttpClient()
        }
        return builder
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder =
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) = Unit

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) = Unit

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            )
            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(
                sslSocketFactory,
                trustAllCerts[0] as X509TrustManager
            )
            builder.hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
}


// Create the Collector
/*      val chuckerCollector = ChuckerCollector(
          context = context,
          showNotification = true,
          retentionPeriod = RetentionManager.Period.ONE_HOUR
      )
      val chuckerInterceptor = ChuckerInterceptor.Builder(context)
          .collector(chuckerCollector)
          .maxContentLength(250_000L)
          .redactHeaders("Auth-Token", "Bearer")
          .alwaysReadResponseBody(true)
          .build()*/
