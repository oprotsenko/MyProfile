package com.protsolo.ui.customViews

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.protsolo.R

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.customButtonStyle
) : View(context, attrs, defStyleAttr) {

    private val colors: List<Int> =
        listOf(Color.BLUE, Color.RED, Color.rgb(251, 188, 5), Color.BLUE, Color.GREEN, Color.RED)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val spaceBetween: Float
    private val letterSpacing: Float
    private val chars: List<Char>
    private val lettersWidth: FloatArray

    private var iconImage: Bitmap
    private var text: String
    private var textSize: Float
    private var textWidth: Float
    private var multicolor: Boolean
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
        textSize = arr.getDimension(R.styleable.CustomButton_android_textSize, 12f)
        paint.color = arr.getColor(R.styleable.CustomButton_android_textColor, colors[0])
        multicolor = arr.getColor(R.styleable.CustomButton_android_textColor, 0) == 0
        paint.typeface = getTypeface(arr, context)
        text = getText(arr)
        iconImage =
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
            startTextX += lettersWidth[i] + letterSpacing
        }
        startTextX = startDrawableX + iconImage.width + spaceBetween
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defWidth = (iconImage.width + textWidth + spaceBetween + letterSpacing * text.length
                + paddingEnd + paddingStart).toInt()
        val defHeight =
            iconImage.height.coerceAtLeast(textSize.toInt()) + paddingBottom + paddingTop

        val initWidth = resolveDefaultSize(widthMeasureSpec, defWidth)
        val initHeight = resolveDefaultSize(heightMeasureSpec, defHeight)
        setMeasuredDimension(initWidth, initHeight)
    }

    private fun resolveDefaultSize(spec: Int, defSize: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> MeasureSpec.getSize(defSize)
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(defSize)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun getText(arr: TypedArray) =
        when (arr.getBoolean(R.styleable.CustomButton_android_textAllCaps, false)) {
            true -> arr.getString(R.styleable.CustomButton_android_text).toString().uppercase()
            false -> arr.getString(R.styleable.CustomButton_android_text).toString()
        }

    private fun getTypeface(
        arr: TypedArray,
        context: Context
    ) = when (arr.getResourceId(R.styleable.CustomButton_android_fontFamily, 0)) {
        0 -> Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        else -> ResourcesCompat.getFont(
            context,
            arr.getResourceId(R.styleable.CustomButton_android_fontFamily, 0)
        )
    }

    private fun getVectorBitmap(context: Context, drawableId: Int): Bitmap? {
        var bitmap: Bitmap? = null
        when (val drawable = ContextCompat.getDrawable(context, drawableId)) {
            is BitmapDrawable -> {
                bitmap = drawable.bitmap
            }
            is VectorDrawable -> {
                bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
        return bitmap
    }
}