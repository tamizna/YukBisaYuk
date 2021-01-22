package com.tamizna.yukbisayuk.roomDb

import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.models.User

interface DatabaseHelper {

    suspend fun insertData(data : DonationTransaction)
    suspend fun getAllData() : List<DonationTransaction>
    suspend fun getUser(username : String, password : String) : List<User>
}