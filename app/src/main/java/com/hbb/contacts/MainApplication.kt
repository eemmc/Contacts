package com.hbb.contacts

import android.content.Context
import androidx.multidex.MultiDex
import com.hbb.contacts.core.BaseApplication

class MainApplication : BaseApplication() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}