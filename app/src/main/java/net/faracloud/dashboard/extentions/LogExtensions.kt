package net.faracloud.dashboard.extentions

import android.util.Log
import net.faracloud.dashboard.BuildConfig

fun Any.logd(message: String?) {
    if (BuildConfig.DEBUG){
        Log.d(" ", "   --> $message")
    }

}

fun Any.loge(message: String?) {
    if (BuildConfig.DEBUG){
        Log.e(this::class.java.toString(), " --> $message")
    }
}