package com.hbb.contacts.core.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.RadioGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat

class NavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RadioGroup(context, attrs) {

    fun addItem(
        @IdRes id: Int,
        @DrawableRes icon: Int,
        title: CharSequence,
        colors: ColorStateList
    ) {
        val item = ItemView(context)
        item.id = id
        item.text = title
        item.setTextColor(colors)
        //
        var drawable = ResourcesCompat.getDrawable(
            resources, icon, context.theme
        )
        drawable = DrawableCompat.wrap(drawable!!).mutate()
        DrawableCompat.setTintList(drawable, colors)
        item.setCompoundDrawablesWithIntrinsicBounds(
            null, drawable, null, null
        )
        //
        this.addView(
            item, LayoutParams(
                0, LayoutParams.MATCH_PARENT, 1.0F
            )
        )
    }

    fun setItemNumber(position: Int, number: Int) {
        val child = getChildAt(position)
        if (child is ItemView) {
            child.setNumber(number)
        }
    }

    class ItemView(context: Context) : AppCompatRadioButton(context) {
        private var mContentHeightOffset = 0

        private var mNumber = 0
        private var mPointSize = 0f
        private var mPointOffsetX = 0f
        private var mPointOffsetY = 0f
        private var mNumberSize = 0f
        private var mNumberOffset = 0f

        private val mPointPaint: Paint
        private val mNumberPaint: Paint

        init {
            this.buttonDrawable = null
            this.gravity = Gravity.CENTER_HORIZONTAL and Gravity.TOP
            this.textAlignment = TEXT_ALIGNMENT_CENTER
            this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
            //
            val drawable = GradientDrawable()
            drawable.setColor(0x78FFFFFF)
            this.background = RippleDrawable(
                ColorStateList.valueOf(0x3F000000),
                null,
                drawable
            )
            //
            mPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mPointPaint.style = Paint.Style.FILL
            mPointPaint.color = 0xFFE82E2E.toInt()
            //
            mNumberPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mNumberPaint.style = Paint.Style.FILL
            mNumberPaint.textAlign = Paint.Align.CENTER
            mNumberPaint.color = Color.WHITE
            mNumberPaint.textScaleX = 0.86F
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)

            val icon = compoundDrawables[1]
            var mContentHeight = 0
            if (icon != null) {
                mContentHeight += icon.bounds.height()
                mContentHeight += compoundDrawablePadding
            }
            mContentHeight += (textSize + paddingTop + paddingBottom).toInt()
            mContentHeightOffset = (measuredHeight - mContentHeight) / 2

            mPointSize = mContentHeight * 0.28f
            mPointOffsetX = measuredWidth * 0.5f + mContentHeight * 0.42f
            mPointOffsetY = measuredHeight * 0.5f - mContentHeight * 0.36f

            mNumberSize = mContentHeight * 0.3f
            mNumberOffset = mNumberSize * 0.36f
            mNumberPaint.textSize = mNumberSize
        }

        fun setNumber(number: Int) {
            this.mNumber = number
            invalidate()
        }

        override fun onDraw(canvas: Canvas) {
            val saveCount = canvas.save()
            canvas.translate(0F, mContentHeightOffset.toFloat())
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)

            if (mNumber > 0) {
                canvas.save()
                canvas.translate(mPointOffsetX, mPointOffsetY)
                canvas.drawCircle(0F, 0F, mPointSize, mPointPaint)
                canvas.drawText(
                    if (mNumber > 99) "99+" else mNumber.toString(),
                    0F, mNumberOffset, mNumberPaint
                )
                canvas.restore()
            }
        }
    }


}