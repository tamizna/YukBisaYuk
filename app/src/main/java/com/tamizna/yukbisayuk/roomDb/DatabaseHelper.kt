package com.tamizna.yukbisayuk.roomDb

import com.tamizna.yukbisayuk.models.DonationTransaction

interface DatabaseHelper {

    suspend fun insertData(data : DonationTransaction)
    suspend fun getAllData() : List<DonationTransaction>
}