package com.tamizna.yukbisayuk.historyDonation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.roomDb.DatabaseHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    val listTransactionDonation = MutableLiveData<DataResult<List<DonationTransaction>>>()

    fun getListTransaction() {
        GlobalScope.launch {
            listTransactionDonation.postValue(DataResult(DataResult.State.LOADING, null, null))

            try {
                val listData = dbHelper.getAllData()
                listTransactionDonation.postValue(
                    DataResult(
                        DataResult.State.SUCCESS,
                        listData,
                        "success"
                    )
                )
            } catch (e: Exception) {
                listTransactionDonation.postValue(
                    DataResult(
                        DataResult.State.ERROR,
                        null,
                        e.message
                    )
                )
            }
        }
    }
}