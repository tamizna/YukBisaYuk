package com.tamizna.yukbisayuk.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.services.NetworkService
import com.tamizna.yukbisayuk.services.Networking

class DetailDonationRepository {

    private val networkService: NetworkService = Networking.service

    fun getDetailDonation(donationId: String): LiveData<DataResult<ResponseGetListDonasiItem>> =
        liveData {
            emit(DataResult<ResponseGetListDonasiItem>(DataResult.State.LOADING, null, null))

            val result = try {
                networkService.getDetailDonation(donationId).convertToDataResult()
            } catch (e: Exception) {
                e.convertExceptionToError()
            }

            Log.d("DETAIL_DONASI", "${result.data}")

            if (result.state == DataResult.State.SUCCESS && result.data != null) {
                result.data.let { item ->
                    ResponseGetListDonasiItem(
                        item.createdAt ?: "",
                        item.currentDonation,
                        item.description ?: "",
                        item.id,
                        item.photo ?: "",
                        item.source_photo ?: "",
                        item.targetDonation,
                        item.title ?: ""
                    )
                }.run {
                    emit(DataResult(DataResult.State.SUCCESS, this, null))
                }
                Log.d("DETAIL_DONASI", "${result.data}")
            } else {
                emit(DataResult(result.state, result.data, result.errorMessage))
            }
        }
}