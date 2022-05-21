package net.faracloud.dashboard.core.api

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
/*
data class Resource<out T>(val status: BaseStatus, val data: T?, val message: String?, val code:Int, val extra: ExtraRepoInterface? = null) {
    companion object {
        fun <T> success(data: T?,code:Int): Resource<T> {
            return Resource(BaseStatus.SUCCESS, data, null,code)
        }

        fun <T> error(msg: String, data: T?, code: Int, extra: ExtraRepoInterface? = null): Resource<T> {
            return Resource(BaseStatus.ERROR, data, msg,code,extra)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(BaseStatus.LOADING, data, null,-1)
        }

        fun <T> idle(data: T?): Resource<T> {
            return Resource(BaseStatus.IDLE, data, null,-1)
        }

    }
}*/
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}