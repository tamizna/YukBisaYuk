package com.tamizna.yukbisayuk.models

data class ResponseGetListDonasiItem(
    var createdAt: String,
    val currentDonation: Double,
    val description: String,
    val id: String,
    val photo: String,
    val source_photo: String,
    val targetDonation: Double,
    val title: String
)