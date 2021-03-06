package com.hbb.contacts.core.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.widget.FrameLayout

class FragmentLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    init {
        setOnHierarchyChangeListener(object : OnHierarchyChangeListener {
            override fun onChildViewAdded(parent: View?, child: View?) {
                requestApplyInsets()
            }

            override fun onChildViewRemoved(parent: View?, child: View?) {
                // do noting.
            }

        })
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets? {
        for (i in 0 until childCount) {
            getChildAt(i).dispatchApplyWindowInsets(insets)
        }

        return insets
    }
}