package com.tamizna.yukbisayuk.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView

object LoadingDialog {
    fun showLoading(context: Context, loadingMessage : String) : Dialog {
        return MaterialDialog(context).apply {
            customView(
                view = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    addView(ProgressBar(context))
                    addView(TextView(context).apply {
                        text= loadingMessage
                        gravity = Gravity.CENTER_HORIZONTAL
                    })
                }
            )
            cancelable(false)
            cancelOnTouchOutside(false)
        }
    }
}