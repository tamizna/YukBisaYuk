package com.tamizna.yukbisayuk.services

import com.tamizna.yukbisayuk.models.ResponseGetListDonasiItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {

    @GET("donasi")
    suspend fun getListDonasi(): Response<List<ResponseGetListDonasiItem>>

    @GET("donasi/{donationId}")
    suspend fun getDetailDonation(@Path("donationId") donationId : String) : Response<ResponseGetListDonasiItem>
}