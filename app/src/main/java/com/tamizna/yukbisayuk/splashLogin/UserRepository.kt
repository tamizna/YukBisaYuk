package com.tamizna.yukbisayuk.splashLogin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tamizna.yukbisayuk.models.DataResult
import com.tamizna.yukbisayuk.models.User
import com.tamizna.yukbisayuk.roomDb.DatabaseBuilder
import com.tamizna.yukbisayuk.roomDb.DatabaseHelper
import com.tamizna.yukbisayuk.roomDb.DatabaseHelperImp
import com.tamizna.yukbisayuk.utils.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers.IO

class UserRepository {

    private val prefs = SharedPreferencesHelper()

    fun checkLoggedIn() : LiveData<Boolean> = liveData(IO) {
        emit(!prefs.getUsername().isNullOrEmpty())
    }

    fun doLogIn(username :String, password : String, dbHelper: DatabaseHelper) : LiveData<DataResult<List<User>>> =
        liveData(IO) {
            emit(DataResult<List<User>>(DataResult.State.LOADING, null, null))

            val result = dbHelper.getUser(username, password)

            if(!result.isNullOrEmpty()) {
                emit(DataResult(DataResult.State.SUCCESS, result, null))
                prefs.setUsername(result.first().userName)
            } else {
                emit(DataResult<List<User>>(DataResult.State.ERROR, null, "Username atau Password salah.\nSilahkan coba kembali"))
            }
        }

    fun logOut() : LiveData<DataResult<Unit>> =
        liveData(IO) {
            emit(DataResult(DataResult.State.LOADING, Unit, null))
            prefs.removeUsername()
            emit(DataResult(DataResult.State.SUCCESS, Unit, null))
        }
}