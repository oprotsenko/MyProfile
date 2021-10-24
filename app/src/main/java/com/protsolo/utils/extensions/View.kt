package com.protsolo.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.dpToPx(dp: Int): Int {
    return (dp.toFloat() * resources.displayMetrics.density).toInt()
}

fun getVectorBitmap(context: Context, drawableId: Int): Bitmap? {
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