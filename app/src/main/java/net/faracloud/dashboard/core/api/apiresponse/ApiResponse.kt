package net.faracloud.dashboard.core.api.apiresponse


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import net.faracloud.dashboard.core.api.BaseModel
import java.lang.reflect.Type

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
sealed class ApiResponse<T> {
    companion object {

        private fun parseBaseModel(jsonString: String?): BaseModel<*>? {
            jsonString ?: return null
            try {
                val gson = Gson()
                val collectionType: Type = object : TypeToken<BaseModel<*>>() {}.type
                return gson.fromJson(jsonString, collectionType)
            } catch (e: Exception) {
                return null
            }

        }

        private fun  getErrorMessage(response: String?): String {
            if (response == null)
                return  "عملیات شما با خطا مواجه شد"
            val baseModel = parseBaseModel(response)
            val msg = baseModel?.error?.message ?: "عملیات شما با خطا مواجه شد"

            return if (msg.isEmpty()) {
                "عملیات شما با خطا مواجه شد"
            } else {
                msg
            }
        }

        private fun getErrorCode(response: String?,responseCode: Int): Int {
            if (response == null)
                return responseCode
            val baseModel = parseBaseModel(response)
            return baseModel?.error?.code ?: responseCode


        }

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            // todo Sentry.captureException(error)
            return ApiErrorResponse("عملیات شما با خطا مواجه شد",null,-1)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            val errorBody = response.errorBody()?.string()
            val responseCode = response.code()
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || responseCode == 204) {
                    ApiEmptyResponse(responseCode)
                } else {
                    ApiSuccessResponse(body, responseCode)
                }
            }   else {
                ApiErrorResponse(
                    getErrorMessage(errorBody) ?: "unknown error",
                    parseBaseModel(errorBody)?.error?.extra,
                    getErrorCode(errorBody,responseCode)
                )
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T>(val code: Int) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T, val code: Int) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String,val extra:Any? , val code: Int) : ApiResponse<T>()
