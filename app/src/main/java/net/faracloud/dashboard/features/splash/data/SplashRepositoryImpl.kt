/*
package net.faracloud.dashboard.features.splash.data

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.faracloud.dashboard.core.api.Resource
import net.faracloud.dashboard.core.mapper.Mapper
import net.faracloud.dashboard.core.sharedpreferences.PreferenceHelper
import net.faracloud.dashboard.features.splash.data.network.SplashApi
import net.faracloud.dashboard.features.splash.data.remoteModel.ConfigRemoteModel
import net.faracloud.dashboard.features.splash.data.repoModel.ConfigRepoModel
import net.faracloud.dashboard.features.splash.domain.SplashRepository
import java.io.IOException
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(
    private val context: Application,
    private val splashApi: SplashApi,
    private val getConfigRemoteRepoMapper:  Mapper<ConfigRemoteModel, ConfigRepoModel>,
    private val preferenceHelper: PreferenceHelper
): SplashRepository {

    override suspend fun getIsFirstTimeAppIsLaunchedValue(): Flow<Boolean> = flow {
        emit(false)
    }

    override suspend fun getVersionCode(): Flow<String> = flow {
        emit("1")
    }

    override suspend fun getConfig(): Resource<ConfigRepoModel> {
        return getConfigMock()
    }

    override suspend fun getUserId(): Flow<Int> = flow {
        emit(-1)
        */
/*
        var id: Int? = null
        userDao.getUser().collect {
            it?.id?.let {
                id = it
            }
            emit(Either.Right(id))
        }*//*

    }


    override suspend fun isLogin(): Flow<Boolean> = flow {
        emit(false)
    }

    */
/**
     * mock
     *//*


    private fun getConfigMock(): Resource<ConfigRepoModel> {
        val jsonFileString = getJsonDataFromAsset(context, "configs.json")
        //Log.e("data", "  $jsonFileString")
        val gson = Gson()
        val configRemoteModelType = object : TypeToken<ConfigRemoteModel>() {}.type
        val configRemoteModel: ConfigRemoteModel = gson.fromJson(jsonFileString, configRemoteModelType)
        return  Resource.success(getConfigRemoteRepoMapper.map(configRemoteModel) , 200)

    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
*/
