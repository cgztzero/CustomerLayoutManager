package cn.com.zt

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

class SlideLayoutManager(cxt: Context) : RecyclerView.LayoutManager() {

    var maxShowCount = 4
    var scaleGap = 0.05f
    var transYGap: Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        20f, cxt.resources.displayMetrics
    )

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)

        val itemCount = itemCount
        val bottomPosition = if (itemCount < maxShowCount) {
            0
        } else {
            itemCount - maxShowCount
        }

        for (i in bottomPosition until itemCount) {
            val view = recycler.getViewForPosition(i)
            if (i == itemCount - 1) {
                view.rotation = 0f
            }

            addView(view)
            measureChildWithMargins(view, 0, 0)

            val widthSpace = width - getDecoratedMeasuredWidth(view)
            val heightSpace = height - getDecoratedMeasuredHeight(view)

            layoutDecoratedWithMargins(
                view, widthSpace / 2, heightSpace / 2,
                widthSpace / 2 + getDecoratedMeasuredWidth(view),
                heightSpace / 2 + getDecoratedMeasuredHeight(view)
            )

            val level = itemCount - i - 1
            if (level > 0) {
                if (level < maxShowCount - 1) {
                    view.translationY = transYGap * level
                    view.scaleX = 1 - scaleGap * level
                    view.scaleY = 1 - scaleGap * level
                } else {
                    view.translationY = transYGap * (level - 1)
                    view.scaleX = 1 - scaleGap * (level - 1)
                    view.scaleY = 1 - scaleGap * (level - 1)
                }
            }
        }
    }
}