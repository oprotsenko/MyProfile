package com.protsolo.utils.extensions

import android.content.Context

fun Context.dpToPx(dp: Int): Int {
    return (dp.toFloat() * this.resources.displayMetrics.density).toInt()
}