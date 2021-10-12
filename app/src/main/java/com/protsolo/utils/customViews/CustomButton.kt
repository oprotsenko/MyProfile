package com.protsolo.utils.customViews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.graphics.drawable.toBitmap
import com.protsolo.R
import java.security.Provider

class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView: TextView

    // fixme do with binding?
    init {
        val root = LayoutInflater.from(context).inflate(R.layout.google_button_layout, null)
        imageView = root.findViewById(R.id.imageViewCustomButtonIcon)
        textView = root.findViewById(R.id.textViewCustomButtonText)
//        loadAttr(attrs, defStyleAttr)
    }

//    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
//        val arr = context.obtainStyledAttributes(
//            attrs,
//            R.styleable.CustomButton
//        )
//        val buttonText = arr.getString(R.styleable.CustomButton_android_text)
//        val buttonImage = arr.getDrawable(R.styleable.CustomButton_srcCompat)
//        arr.recycle()
//        textView.text = buttonText
//        imageView.setImageBitmap(buttonImage?.toBitmap())
//        this.setBackgroundDrawable(buttonImage)
//    }
}