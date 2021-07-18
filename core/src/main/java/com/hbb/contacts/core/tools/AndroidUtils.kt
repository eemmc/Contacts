package com.hbb.contacts.core.tools

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Pattern

object AndroidUtils {
    private const val TAG = "AndroidTools"

    /**
     * 显示ShortToast
     *
     * @param context 上下文引用
     * @param content 显示内容
     */
    @UiThread
    fun showShortToast(
        context: Context,
        content: String?,
        gravity: Int = Gravity.CENTER_VERTICAL
    ) {
        val toast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            toast.setGravity(gravity, 0, 0)
        }
        toast.show()
    }

    /**
     * 显示LongToast
     *
     * @param context 上下文引用
     * @param content 显示内容
     */
    @UiThread
    fun showLongToast(
        context: Context,
        content: String?,
        gravity: Int = Gravity.CENTER_VERTICAL
    ) {
        val toast = Toast.makeText(context, content, Toast.LENGTH_LONG)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            toast.setGravity(gravity, 0, 0)
        }
        toast.show()
    }


    /**
     * 显示软键盘
     *
     * @param edit 当前文本输入框
     */
    @UiThread
    fun showSoftInput(edit: EditText) {
        edit.requestFocus()
        val imm = edit.context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager

        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏当前窗口的软键盘
     * @param window 当前窗口对象
     */
    @UiThread
    fun hideSoftInput(window: Window) {
        val view = window.peekDecorView()
        if (view != null) {
            val imm = view.context.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * 获取AndroidManifest.xml中配置的全局参数
     *
     * @param context 上下文引用
     * @param keyName 参数名
     * @return 参数值
     */
    fun loadApplicationData(
        context: Context,
        keyName: String?
    ): String? {
        var result: String? = null
        try {
            val info: ApplicationInfo
            val appContext = context.applicationContext
            info = appContext.packageManager.getApplicationInfo(
                appContext.packageName,
                PackageManager.GET_META_DATA
            )
            //
            result = info.metaData.getString(keyName)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "loadApplicationData", e)
        }
        return result
    }

    /**
     * 获取设备CPU序列号
     *
     * @return CPU序列号
     */
    fun loadCPUSerialCode(): String {
        var result = "0000000000000000"

        var reader: InputStreamReader? = null
        var bufferedReader: BufferedReader? = null
        try {
            //
            val process = Runtime.getRuntime().exec("cat /proc/cpuinfo")
            reader = InputStreamReader(process.inputStream)
            bufferedReader = BufferedReader(reader)
            //
            var buffer: String?
            while (bufferedReader.readLine().also { buffer = it } != null) {
                if (buffer!!.contains("Serial")) {
                    val mark = buffer!!.indexOf(":")
                    result = buffer!!.substring(mark + 1).trim()
                    break
                }
            }

            process.destroy()
        } catch (ex: Exception) {
            Log.e(TAG, "getCPUSerialCode", ex)
        } finally {
            try {
                bufferedReader?.close()
            } catch (ex: IOException) {
                //do nothing.
            }

            try {
                reader?.close()
            } catch (ex: IOException) {
                //do nothing.
            }
        }

        return result
    }

    /**
     * 获取当前应用的版本名
     *
     * @param context 上下文引用
     * @return 当前应用的版本名
     */
    fun getAppVersionName(context: Context): String {
        var versionName = ""
        try {
            val appContext = context.applicationContext
            val manage = appContext.packageManager
            val info = manage.getPackageInfo(appContext.packageName, 0)
            versionName = info.versionName
        } catch (e: Exception) {
            Log.e(TAG, "getAppVersionName", e)
        }
        return versionName
    }

    /**
     * 获取当前应用的内部版本号
     *
     * @param context 主下文引用
     * @return 当前应用的内部版本号
     */
    @Suppress("deprecation")
    fun getAppVersionCode(context: Context): Long {
        var versionCode = 0L
        try {
            val appContext = context.applicationContext
            val manage = appContext.packageManager
            val info = manage.getPackageInfo(appContext.packageName, 0)
            versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                info.longVersionCode
            } else {
                info.versionCode.toLong()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 版本名比较
     * @param local 本地版本名
     * @param remote 目标版本名
     * @return 目标版本是否比本地版本名大
     */
    fun compareVersionName(
        local: String?,
        remote: String?
    ): Boolean {

        if (TextUtils.isEmpty(remote) || TextUtils.isEmpty(local)) {
            return false
        }

        val lefts = local!!.split("\\.".toRegex())
        val rights = remote!!.split("\\.".toRegex())

        try {
            val size = (lefts.size).coerceAtMost(rights.size)

            var result: Int
            for (i in 0 until size) {
                result = lefts[i].toInt() - rights[i].toInt()
                if (result != 0) {
                    return result < 0
                }
            }
        } catch (e: Exception) {
            //do nothing.
        }

        return false
    }

    /**
     * 单位转换dp转px
     *
     * @param context 上下文引用
     * @param dpValue dip数值
     * @return px数值
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 单位转换sp转px
     *
     * @param context 上下文引用
     * @param spValue sp数值
     * @return px数值
     */
    fun sp2px(context: Context, spValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spValue,
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 给TextView 组件添加下滑线显示
     *
     * @param text TextView及其子类
     */
    fun setTextViewUnderline(text: TextView) {
        text.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        text.paint.isAntiAlias = true
    }

    /**
     * 获取系统默认的StatusBar高度
     * @param context 上下文引用
     * @return 系统默认的StatusBar高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resId = context.resources.getIdentifier(
            "status_bar_height", "dimen", "android"
        )
        return if (resId > 0) {
            context.resources.getDimensionPixelSize(resId)
        } else {
            0
        }
    }
}