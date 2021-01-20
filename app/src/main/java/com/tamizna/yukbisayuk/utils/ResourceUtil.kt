package com.tamizna.yukbisayuk.utils

import com.tamizna.yukbisayuk.CustomApplication

object ResourceUtil {
    fun getString(resourceId : Int) : String {
        return CustomApplication.application.resources.getString(resourceId)
    }
}