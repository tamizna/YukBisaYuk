package com.tamizna.yukbisayuk.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.tamizna.yukbisayuk.R

class DialogLoading(context: Context?) : Dialog(context!!)
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } catch (e: Exception) {

        }
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
    }
}