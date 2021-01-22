package com.tamizna.yukbisayuk.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import com.tamizna.yukbisayuk.splashLogin.UserRepository
import com.tamizna.yukbisayuk.utils.ResourceUtil

class HomeViewModel : ViewModel() {
    private val listDonasiRepository: ListDonasiRepository = ListDonasiRepository()
    private val userRepository: UserRepository = UserRepository()

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

    val loggingOut: LiveData<DataResult<Unit>> =
        userRepository.logOut().switchMap {
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

    fun filteredData(
        data: List<ResponseGetListDonasiItem>,
        pos: Any
    ): List<ResponseGetListDonasiItem> {
        val category = ResourceUtil.getStringArray(R.array.donation_category)
        return when (pos) {
            category[1] -> {
                data.sortedBy {
                    it.title
                }
            }
            category[2] -> {
                data.sortedByDescending { it.createdAt }
            }
            category[3] -> {
                data.sortedBy { it.currentDonation }
            }
            else -> {
                data
            }
        }
    }
}