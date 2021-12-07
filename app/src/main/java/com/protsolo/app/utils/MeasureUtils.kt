package com.protsolo.app.utils

import android.view.View

class MeasureUtils {

    fun resolveDefaultSize(spec: Int, defSize: Int): Int {
        return when (View.MeasureSpec.getMode(spec)) {
            View.MeasureSpec.UNSPECIFIED -> View.MeasureSpec.getSize(defSize)
            View.MeasureSpec.AT_MOST -> View.MeasureSpec.getSize(defSize)
            View.MeasureSpec.EXACTLY -> View.MeasureSpec.getSize(spec)
            else -> View.MeasureSpec.getSize(spec)
        }

    }
}