package com.tamizna.yukbisayuk.splashLogin

import androidx.lifecycle.*
import com.tamizna.yukbisayuk.R
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.roomDb.DatabaseHelper
import com.tamizna.yukbisayuk.utils.ResourceUtil
import kotlinx.coroutines.Dispatchers.IO

class LoginViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    val usernameValidation = MutableLiveData<DataResult<Unit>>()
    val passwordValidation = MutableLiveData<DataResult<Unit>>()
    private val isValidate: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private val userRepository = UserRepository()

    fun checkFormLogin(username: String, password: String) {
        var validated = true
        if (username.isBlank()) {
            validated = false
            usernameValidation.postValue(
                DataResult(
                    DataResult.State.ERROR, Unit, ResourceUtil.getString(
                        R.string.masukkan_username
                    )
                )
            )
        } else {
            usernameValidation.postValue(DataResult(DataResult.State.SUCCESS, Unit, null))
        }

        if (password.isBlank()) {
            validated = false
            passwordValidation.postValue(
                DataResult(
                    DataResult.State.ERROR,
                    Unit,
                    ResourceUtil.getString(R.string.masukkan_password)
                )
            )
        } else {
            passwordValidation.postValue(DataResult(DataResult.State.SUCCESS, Unit, null))
        }

        if (validated) {
            isValidate.postValue(Pair(username, password))
        }
    }

    val login: LiveData<DataResult<Unit>> = isValidate.switchMap {
        userRepository.doLogIn(it.first, it.second, dbHelper)
    }.switchMap {
        liveData(IO) {
            emit(DataResult(it.state, Unit, it.errorMessage))
        }
    }
}