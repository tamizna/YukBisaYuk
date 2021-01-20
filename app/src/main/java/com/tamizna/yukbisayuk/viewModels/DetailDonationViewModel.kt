package com.tamizna.yukbisayuk.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.repository.DetailDonationRepository

class DetailDonationViewModel(donationId : String) : ViewModel() {

    private val detailDonationRepository : DetailDonationRepository = DetailDonationRepository()

    val detailDonation : LiveData<DataResult<ResponseGetListDonasiItem>> = detailDonationRepository.getDetailDonation(donationId).switchMap {
        liveData {
            emit(DataResult(it.state, it.data, it.errorMessage))
        }
    }
}