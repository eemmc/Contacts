package com.hbb.contacts.core.ui.dialog

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentManager
import com.hbb.contacts.core.R

class WaitingDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AppCompatDialog(context, R.style.Theme_Contacts_BaseDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val d4Size = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4F,
            resources.displayMetrics
        )
        //
        val layout = LinearLayout(inflater.context)
        layout.orientation = LinearLayout.HORIZONTAL
        layout.gravity = Gravity.CENTER
        layout.setPadding((d4Size * 3).toInt())
        layout.background = GradientDrawable().apply {
            this.color = ColorStateList.valueOf(0x99000000.toInt())
            this.cornerRadius = d4Size * 2
        }
        //
        val progressBar = ProgressBar(inflater.context)
        val size = (d4Size * 8).toInt()
        layout.addView(progressBar, size, size)
        if (progressBar.indeterminateDrawable != null) {
            val drawable = DrawableCompat.wrap(progressBar.indeterminateDrawable)
            DrawableCompat.setTint(drawable, 0xFFC43652.toInt())
            progressBar.indeterminateDrawable = drawable
        }
        //
        val text = AppCompatTextView(inflater.context)
        text.setPadding((d4Size * 2).toInt())
        text.setTextColor(Color.WHITE)
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
        text.text = resources.getText(R.string.doing)
        layout.addView(text)
        //
        return layout
    }

    companion object {
        private const val TAG = "WaitingDialog"

        fun show(manager: FragmentManager): WaitingDialog {
            val waiting = WaitingDialog()
            waiting.show(manager, TAG)
            return waiting
        }
    }
}