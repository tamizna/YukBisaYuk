package com.tamizna.yukbisayuk.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "donation_transaction")
data class DonationTransaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    val transactionId: Int,

    @ColumnInfo(name = "donation_id")
    val donationId: String,

    @ColumnInfo(name = "donation_title")
    val donationTitle: String,

    @ColumnInfo(name = "nominal")
    val nominal: Int,

    @ColumnInfo(name = "date")
    val date : Date?
)
