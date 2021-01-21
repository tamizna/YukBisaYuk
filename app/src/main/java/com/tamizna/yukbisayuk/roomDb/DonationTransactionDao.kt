package com.tamizna.yukbisayuk.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tamizna.yukbisayuk.models.DonationTransaction

@Dao
interface DonationTransactionDao {
    @Query("SELECT * from donation_transaction")
    fun getAllTransaction() : List<DonationTransaction>

    @Insert
    fun saveTransaction(data : DonationTransaction)
}