package com.hbb.contacts.core.ui.base

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private val mHandler = Handler(Looper.getMainLooper())

    fun requireActivity(): BaseActivity {
        return this
    }

    @Suppress("unused")
    fun postOnMainThread(runnable: () -> Unit) {
        mHandler.post(runnable)
    }

    @Suppress("unused")
    fun postOnMainThreadDelayed(duration: Long, runnable: () -> Unit) {
        mHandler.postDelayed(runnable, duration)
    }

    @Suppress("unused")
    fun postOnMainThread(runnable: Runnable) {
        mHandler.post(runnable)
    }

    @Suppress("unused")
    fun postOnMainThreadDelayed(duration: Long, runnable: Runnable) {
        mHandler.postDelayed(runnable, duration)
    }

    @Suppress("unused")
    fun removeMainThreadCallbacks(runnable: Runnable) {
        mHandler.removeCallbacks(runnable)
    }

    companion object {
        const val NO_TITLE = ""
    }
}