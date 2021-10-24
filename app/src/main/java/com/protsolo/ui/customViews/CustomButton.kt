package com.protsolo.ui.customViews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.protsolo.R
import com.protsolo.utils.extensions.getVectorBitmap

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val colors: List<Int> =
        listOf(Color.BLUE, Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.RED)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val spaceBetween: Float
    private val letterSpacing: Float
    private val chars: List<Char>
    private val lettersWidth: FloatArray

    private var mImage: Bitmap
    private var text: String
    private var textSize: Float
    private var textWidth: Float
    private var multicolor = true
    private var startDrawableX: Float = 0f
    private var startDrawableY: Float = 0f
    private var startTextX: Float = 0f
    private var startTextY: Float = 0f


    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0)
        textSize = arr.getDimension(R.styleable.CustomButton_android_textSize, 12f)
        paint.color =
            arr.getColor(R.styleable.CustomButton_android_textColor, colors[0])
        if (arr.getColor(R.styleable.CustomButton_android_textColor, 0) != 0)
            multicolor = false
        paint.typeface = ResourcesCompat.getFont(
            context,
            arr.getResourceId(R.styleable.CustomButton_android_fontFamily, 0)
        )
        text = when (arr.getBoolean(R.styleable.CustomButton_android_textAllCaps, false)) {
            true -> arr.getString(R.styleable.CustomButton_android_text).toString().uppercase()
            false -> arr.getString(R.styleable.CustomButton_android_text).toString()
        }
        mImage =
            getVectorBitmap(context, arr.getResourceId(R.styleable.CustomButton_srcCompat, 0))!!
        spaceBetween = arr.getDimension(R.styleable.CustomButton_spaceBetween, 0f)
        letterSpacing = arr.getFloat(R.styleable.CustomButton_android_letterSpacing, 0f)
        isEnabled = arr.getBoolean(R.styleable.CustomButton_android_enabled, true)
        isClickable = true
        arr.recycle()

        paint.textSize = textSize
        paint.style = Paint.Style.FILL_AND_STROKE
        textWidth = paint.measureText(text)
        lettersWidth = FloatArray(text.length)
        paint.getTextWidths(text, lettersWidth)
        chars = text.map { it }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val textWidth = paint.measureText(text)
        startDrawableX = (width - mImage.width.toFloat() - textWidth - spaceBetween) / 2
        startDrawableY = (height - mImage.height.toFloat()) / 2
        startTextX = startDrawableX + mImage.width + spaceBetween
        startTextY = height / 2 + paint.fontMetrics.descent
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(mImage, startDrawableX, startDrawableY, null)

        for (i in chars.indices) {
            canvas?.drawText(chars[i].toString(), startTextX, startTextY, paint)
            startTextX += lettersWidth[i] + letterSpacing
            if (multicolor) {
                paint.color = colors[i % chars.size]
            }
        }
        startTextX = startDrawableX + mImage.width + spaceBetween
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defWidth = (mImage.width + textWidth + spaceBetween + letterSpacing * text.length
                + paddingEnd + paddingStart).toInt()
        val defHeight = mImage.height.coerceAtLeast(textSize.toInt()) + paddingBottom + paddingTop

        val initWidth = resolveDefaultSpec(widthMeasureSpec, defWidth)
        val initHeight = resolveDefaultSpec(heightMeasureSpec, defHeight)
        setMeasuredDimension(initWidth, initHeight)
    }

    private fun resolveDefaultSpec(spec: Int, defSpec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> MeasureSpec.getSize(defSpec)
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(defSpec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }
}