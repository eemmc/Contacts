package com.hbb.contacts.core.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    protected fun getBaseActivity(): BaseActivity? {
        val target = this.activity
        return if (target is BaseActivity) target else null
    }

    protected fun requireBaseActivity(): BaseActivity {
        return getBaseActivity()
            ?: throw IllegalStateException("Fragment $this not attached to an BaseActivity.")
    }
}