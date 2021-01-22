package com.tamizna.yukbisayuk.roomDb

import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.models.User

class DatabaseHelperImp(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun insertData(data: DonationTransaction) {
        appDatabase.transactionDao().saveTransaction(data)
    }

    override suspend fun getAllData(): List<DonationTransaction> =
        appDatabase.transactionDao().getAllTransaction()

    override suspend fun getUser(username: String, password: String): List<User> =
        appDatabase.userDao().getUser(username, password)

}