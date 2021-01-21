package com.tamizna.yukbisayuk.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.repository.ListDonasiRepository

class DonasiViewModel : ViewModel() {
    private val listDonasiRepository: ListDonasiRepository = ListDonasiRepository()

    val donasi: LiveData<DataResult<List<ResponseGetListDonasiItem>>> =
        listDonasiRepository.getListDonasi().switchMap {
            liveData {
                emit(
                    DataResult(
                        it.state,
                        it.data,
                        it.errorMessage
                    )
                )
            }
        }
}