package com.tamizna.yukbisayuk.utils

import com.tamizna.yukbisayuk.CustomApplication
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object ResourceUtil {
    fun getString(resourceId : Int) : String {
        return CustomApplication.application.resources.getString(resourceId)
    }

    fun thousandSeparatorRupiah(value: String?): String {
        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        formatter.applyPattern("#,###,###,###")
        val str = formatter.format(value!!.toLong())
        return str
    }
}