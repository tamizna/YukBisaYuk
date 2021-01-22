package com.tamizna.yukbisayuk.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tamizna.yukbisayuk.models.DonationTransaction
import com.tamizna.yukbisayuk.models.User
import com.tamizna.yukbisayuk.utils.DateConverter

@Database(entities = [DonationTransaction::class, User::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun transactionDao() : DonationTransactionDao
    abstract fun userDao() : UserDao
}