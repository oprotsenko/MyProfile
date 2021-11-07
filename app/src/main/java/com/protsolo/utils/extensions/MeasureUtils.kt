package com.protsolo.utils.extensions

import android.view.View

object MeasureUtils {

    fun resolveDefaultSize(spec: Int, defSize: Int): Int {
        return when (View.MeasureSpec.getMode(spec)) {
            View.MeasureSpec.UNSPECIFIED -> View.MeasureSpec.getSize(defSize)
            View.MeasureSpec.AT_MOST -> View.MeasureSpec.getSize(defSize)
            View.MeasureSpec.EXACTLY -> View.MeasureSpec.getSize(spec)
            else -> View.MeasureSpec.getSize(spec)
        }

    }
}