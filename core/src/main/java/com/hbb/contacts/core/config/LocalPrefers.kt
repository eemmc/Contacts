package com.hbb.contacts.core.config


import android.content.Context
import android.content.SharedPreferences
import java.lang.ref.WeakReference

object LocalPrefers {
    private const val SAVE_NAME = "config"
    private var sLocalRefs: WeakReference<SharedPreferences>? = null

    private fun getPreferences(context: Context): SharedPreferences {
        var references: SharedPreferences?
        //
        synchronized(SAVE_NAME) {
            if (sLocalRefs?.get().also { references = it } == null) {
                references = context.getSharedPreferences(
                    SAVE_NAME, Context.MODE_PRIVATE
                )
                sLocalRefs = WeakReference(references!!)
            }
        }
        //
        return references!!
    }

    fun loadBoolean(
        context: Context,
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        return try {
            getPreferences(context).getBoolean(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun saveBoolean(
        context: Context,
        key: String?,
        value: Boolean
    ) {
        getPreferences(context).edit().putBoolean(key, value).apply()
    }

    fun loadInt(context: Context, key: String?, defaultValue: Int): Int {
        return try {
            getPreferences(context).getInt(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun saveInt(context: Context, key: String?, value: Int) {
        getPreferences(context).edit().putInt(key, value).apply()
    }

    fun loadLong(
        context: Context,
        key: String?,
        defaultValue: Long
    ): Long {
        return try {
            getPreferences(context).getLong(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun saveLong(
        context: Context,
        key: String?,
        value: Long
    ) {
        getPreferences(context).edit().putLong(key, value).apply()
    }

    fun loadFloat(
        context: Context,
        key: String?,
        defaultValue: Float
    ): Float {
        return try {
            getPreferences(context).getFloat(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }

    }

    fun saveFloat(
        context: Context,
        key: String?,
        value: Float
    ) {
        getPreferences(context).edit().putFloat(key, value).apply()
    }

    fun loadString(
        context: Context,
        key: String?,
        defaultValue: String?
    ): String? {
        return try {
            getPreferences(context).getString(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun saveString(
        context: Context,
        key: String?,
        value: String?
    ) {
        getPreferences(context).edit().putString(key, value).apply()
    }

    fun loadStringSet(
        context: Context,
        key: String?,
        defaultValue: Set<String>?
    ): Set<String>? {
        return try {
            getPreferences(context).getStringSet(key, defaultValue)
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun saveStringSet(
        context: Context,
        key: String?,
        value: Set<String>?
    ) {
        getPreferences(context).edit().putStringSet(key, value).apply()
    }
}