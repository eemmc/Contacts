package com.hbb.contacts.core

import android.app.Application
import android.content.Context
import com.hbb.contacts.core.config.UserData

abstract class BaseApplication : Application() {
    private lateinit var mUserData: UserData

    override fun onCreate() {
        super.onCreate()
        //
        mUserData = UserData()
        //
    }

    fun getUserData(): UserData {
        return mUserData
    }

    companion object {
        fun from(context: Context): BaseApplication {
            val appContext = context.applicationContext
            if (appContext is BaseApplication) {
                return appContext
            } else {
                throw IllegalAccessError()
            }
        }
    }

}