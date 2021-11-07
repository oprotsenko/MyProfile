package com.protsolo.ui.customViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat.getColor
import com.protsolo.R
import com.protsolo.utils.Constants.DEFAULT_TEXT_SIZE
import com.protsolo.utils.extensions.CustomViewUtils
import com.protsolo.utils.extensions.MeasureUtils

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.customButtonStyle
) : View(context, attrs, defStyleAttr) {

    private val colors: List<Int> =
        listOf(
            Color.BLUE,
            Color.RED,
            getColor(resources, R.color.google_yellow, context.theme),
            Color.BLUE,
            Color.GREEN,
            Color.RED
        )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val spaceBetween: Float
    private val chars: List<Char>
    private val lettersWidth: FloatArray
    private val iconImage: Bitmap
    private val text: String
    private val textSize: Float
    private val textWidth: Float
    private val multicolor: Boolean

    private var startDrawableX: Float = 0f
    private var startDrawableY: Float = 0f
    private var startTextX: Float = 0f
    private var startTextY: Float = 0f


    init {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomButton,
            defStyleAttr,
            R.style.CustomButtonStyle
        )
        textSize = arr.getDimension(R.styleable.CustomButton_android_textSize, DEFAULT_TEXT_SIZE)
        paint.color = arr.getColor(R.styleable.CustomButton_android_textColor, colors[0])
        paint.letterSpacing = arr.getFloat(R.styleable.CustomButton_android_letterSpacing, 0f)
        multicolor = arr.getColor(R.styleable.CustomButton_android_textColor, 0) == 0
        paint.typeface = CustomViewUtils.getTypeface(arr, context)
        text = getText(arr)
        spaceBetween = arr.getDimension(R.styleable.CustomButton_spaceBetween, 0f)
        iconImage =
            CustomViewUtils.getVectorBitmap(
                context,
                arr.getResourceId(R.styleable.CustomButton_srcCompat, 0)
            )
        isEnabled = true
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
        startDrawableX = (width - iconImage.width.toFloat() - textWidth - spaceBetween) / 2
        startDrawableY = (height - iconImage.height.toFloat()) / 2
        startTextX = startDrawableX + iconImage.width + spaceBetween
        startTextY = height / 2 + paint.fontMetrics.descent
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(iconImage, startDrawableX, startDrawableY, null)

        for (i in colors.indices) {
            if (multicolor) {
                paint.color = colors[i % colors.size]
            }
            canvas?.drawText(chars[i].toString(), startTextX, startTextY, paint)
            startTextX += lettersWidth[i] + paint.letterSpacing
        }
        startTextX = startDrawableX + iconImage.width + spaceBetween
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defWidth = (iconImage.width + textWidth + spaceBetween + paint.letterSpacing * text.length
                + paddingEnd + paddingStart).toInt()
        val defHeight =
            iconImage.height.coerceAtLeast(textSize.toInt()) + paddingBottom + paddingTop

        val initWidth = MeasureUtils.resolveDefaultSize(widthMeasureSpec, defWidth)
        val initHeight = MeasureUtils.resolveDefaultSize(heightMeasureSpec, defHeight)
        setMeasuredDimension(initWidth, initHeight)
    }

    private fun getText(arr: TypedArray) =
        when (arr.getBoolean(R.styleable.CustomButton_android_textAllCaps, false)) {
            true -> arr.getString(R.styleable.CustomButton_android_text).toString().uppercase()
            false -> arr.getString(R.styleable.CustomButton_android_text).toString()
        }
}