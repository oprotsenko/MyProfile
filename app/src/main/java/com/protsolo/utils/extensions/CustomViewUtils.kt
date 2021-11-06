package com.protsolo.utils.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.protsolo.R

object CustomViewUtils {

    fun getVectorBitmap(context: Context, drawableId: Int): Bitmap {
        var bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
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

    fun getTypeface(
        arr: TypedArray,
        context: Context
    ) = when (arr.getResourceId(R.styleable.CustomButton_android_fontFamily, 0)) {
        0 -> Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        else -> ResourcesCompat.getFont(
            context,
            arr.getResourceId(R.styleable.CustomButton_android_fontFamily, 0)
        )
    }

}