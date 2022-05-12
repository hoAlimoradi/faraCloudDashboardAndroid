package net.faracloud.dashboard.core.api.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import net.faracloud.dashboard.core.api.AuthService
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import javax.inject.Inject

class AuthenticatorInterceptor @Inject constructor(
    private val preferenceHandler: PreferenceHelper,
    private val authService: AuthService
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            try {
                val refreshToken =
                    authService.refreshToken(
                        preferenceHandler.getRefreshToken()
                    ).execute()
                if (refreshToken.isSuccessful) {
                    refreshToken.body()?.let { body ->
                        body.data.accessToken?.let { accessToken ->
                            preferenceHandler.setAccessToken(accessToken)
                            return@let response.request.newBuilder().header(
                                "Authorization",
                                "Bearer $accessToken"
                            ).build()
                        }
                    }
                } else {
                    preferenceHandler.setAccessToken("")
                    preferenceHandler.setRefreshToken("")
                }
            } catch (e: Exception) {
                preferenceHandler.setAccessToken("")
                preferenceHandler.setRefreshToken("")
            }
            return null
        }
    }


}