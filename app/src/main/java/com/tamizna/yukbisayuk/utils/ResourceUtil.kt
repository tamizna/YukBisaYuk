package com.tamizna.yukbisayuk.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tamizna.yukbisayuk.CustomApplication
import com.tamizna.yukbisayuk.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object ResourceUtil {

    fun getString(resourceId : Int) : String {
        return CustomApplication.application.resources.getString(resourceId)
    }

    fun getStringArray(resourceId : Int) : Array<String> {
        return CustomApplication.application.resources.getStringArray(resourceId)
    }

    fun thousandSeparatorRupiah(value: String?): String {
        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
        formatter.applyPattern("#,###,###,###")
        val str = formatter.format(value!!.toLong())
        return str
    }

    fun showCustomDialog(context: Context, titleText: String, message: String, type: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_global)

        val title = dialog.findViewById<TextView>(R.id.dialogTitle)
        val msg = dialog.findViewById<TextView>(R.id.dialogMessage)
        val buttonOk = dialog.findViewById<Button>(R.id.btnDialogOk)
        val image = dialog.findViewById<ImageView>(R.id.imgIconDialog)

        title.text = titleText
        msg.text = message
        buttonOk.setOnClickListener { dialog.dismiss() }

        if(type == "SUCCESS") {
            Glide.with(context).load(R.drawable.ic_check).into(image)
        } else {
            Glide.with(context).load(R.drawable.ic_wrong).into(image)
        }

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()
    }
}