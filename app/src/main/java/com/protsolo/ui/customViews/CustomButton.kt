package com.protsolo.ui.customViews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.drawToBitmap
import com.protsolo.R

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.layout.custom_button_layout
) : AppCompatButton(context, attrs, defStyleAttr) {

    private var imageView: ImageView = rootView.findViewById(R.id.imageViewCustomButtonIcon)
    private var textView: TextView = rootView.findViewById(R.id.textViewCustomButtonText)

    init {
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomButton,
            defStyleAttr,
            0
        )
        textView.text = arr.getText(R.styleable.CustomButton_android_text)
        imageView.setImageDrawable(arr.getDrawable(R.styleable.CustomButton_srcCompat))
        arr.recycle()
    }
}