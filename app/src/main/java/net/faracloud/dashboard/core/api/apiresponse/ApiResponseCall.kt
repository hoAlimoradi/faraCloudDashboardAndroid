package net.faracloud.dashboard.core.api.apiresponse

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ApiResponseCall<R>(private val delegate: Call<R>) : Call<ApiResponse<R>>{

    override fun enqueue(callback: Callback<ApiResponse<R>>) {
        return delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.create(response))
                )
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.create(t))
                )
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone() = ApiResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResponse<R>> {
        throw UnsupportedOperationException("ApiResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

}