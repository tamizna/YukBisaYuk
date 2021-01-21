package com.tamizna.yukbisayuk.roomDb

import com.tamizna.yukbisayuk.models.DonationTransaction

class DatabaseHelperImp(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun insertData(data: DonationTransaction) {
        appDatabase.transactionDao().saveTransaction(data)
    }

    override suspend fun getAllData() : List<DonationTransaction> {
        return appDatabase.transactionDao().getAllTransaction()
    }
}