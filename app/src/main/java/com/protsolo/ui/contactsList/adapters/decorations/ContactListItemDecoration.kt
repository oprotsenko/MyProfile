package com.protsolo.ui.contactsList.adapters.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Draws obtained margin between items
 */
class ContactListItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        with(outRect) {
            val middle = margin / 2
            top = middle
            bottom = middle
        }
    }
}