package com.tamizna.yukbisayuk.repository

import android.util.Log
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.utils.ResourceUtil
import retrofit2.Response
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

fun <T> Response<T>.convertToDataResult(): DataResult<T> {
    try {
        if (isSuccessful) {
            val body = body()
            if (body != null) return DataResult(DataResult.State.SUCCESS, body, null)
        } else {
            when (this.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                    return DataResult(DataResult.State.UNAUTHORIZED, null, ResourceUtil.getString(R.string.server_connection_error))
                }
                HttpsURLConnection.HTTP_UNAVAILABLE ->
                    return DataResult(DataResult.State.ERROR, null, ResourceUtil.getString(R.string.network_server_not_available))
                else -> {
                    return DataResult(DataResult.State.ERROR, null, ResourceUtil.getString(R.string.network_internal_error))
                }
            }
        }
        return DataResult(DataResult.State.ERROR, null, "")
    } catch (e: Exception) {
        return e.convertExceptionToError()
    }
}

fun <T> Exception.convertExceptionToError(): DataResult<T> {
    return if(this is UnknownHostException) {
        DataResult(
            DataResult.State.ERROR,
            null,
            ResourceUtil.getString(R.string.network_connection_error)
        )
    } else {
        DataResult(
            DataResult.State.ERROR,
            null,
            ResourceUtil.getString(R.string.network_default_error)
        )
    }
}