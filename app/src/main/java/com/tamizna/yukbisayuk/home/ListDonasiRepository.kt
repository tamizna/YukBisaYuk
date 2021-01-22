package com.tamizna.yukbisayuk.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.repository.convertExceptionToError
import com.tamizna.yukbisayuk.repository.convertToDataResult
import com.tamizna.yukbisayuk.services.NetworkService
import com.tamizna.yukbisayuk.services.Networking

class ListDonasiRepository {

    private val networkService: NetworkService = Networking.service

    fun getListDonasi(): LiveData<DataResult<List<ResponseGetListDonasiItem>>> =
        liveData {
            emit(DataResult<List<ResponseGetListDonasiItem>>(DataResult.State.LOADING, null, null))

            val result =
                try {
                    networkService.getListDonasi().convertToDataResult()
                } catch (e: Exception) {
                    e.convertExceptionToError()
                }

            if (result.state == DataResult.State.SUCCESS && result.data != null) {
                result.data.map { item ->
                    ResponseGetListDonasiItem(
                        item.createdAt?:"",
                        item.currentDonation,
                        item.description?:"",
                        item.id,
                        item.photo?:"",
                        item.source_photo?:"",
                        item.targetDonation,
                        item.title?:""
                    )
                }.run {
                    emit(
                        DataResult(
                            DataResult.State.SUCCESS,
                            this,
                            null
                        )
                    )
                }
                Log.d("DONASI", "${result.data}")
            } else {
                emit(DataResult(result.state, result.data, result.errorMessage))
            }
        }
}