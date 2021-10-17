package com.protsolo.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import com.protsolo.R

class CustomButton1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.layout.custom_button_layout
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var imageView: ImageView
    private var textView: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.custom_button_layout, this)
        imageView = root.findViewById(R.id.imageViewCustomButtonIcon)
        textView = root.findViewById(R.id.textViewCustomButtonText)
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
        isEnabled = arr.getBoolean(R.styleable.CustomButton_android_enabled, true)
        isClickable = true
        arr.recycle()
    }
}