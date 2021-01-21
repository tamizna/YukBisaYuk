package com.tamizna.yukbisayuk.inputNominal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.roomDb.DatabaseHelper
import com.tamizna.yukbisayuk.utils.ResourceUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InputNominalViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    val dataDonation = MutableLiveData<DataResult<String>>()
    val nominalValidation = MutableLiveData<DataResult<Unit>>()

    fun checkInputNominal(nominal: String) {
        if(nominal.isBlank()) {
            nominalValidation.postValue(DataResult(DataResult.State.ERROR, Unit, ResourceUtil.getString(
                R.string.masukkan_nominal_donasi)))
        } else {
            nominalValidation.postValue(DataResult(DataResult.State.SUCCESS, Unit, null))
        }
    }

    fun saveDonation(data: DonationTransaction) {
        GlobalScope.launch {
            dataDonation.postValue(DataResult(DataResult.State.LOADING, null, null))

            try {
                dbHelper.insertData(data)
                dataDonation.postValue(DataResult(DataResult.State.SUCCESS, "", "success"))
            } catch (e: Exception) {
                dataDonation.postValue(DataResult(DataResult.State.ERROR, null, e.message))
            }
        }
    }

}