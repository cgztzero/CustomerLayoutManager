package cn.com.zt

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.sqrt

class SlideCallBack(callBack: OnSwipeCallBack, manager: SlideLayoutManager) :
    ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.DOWN
                or ItemTouchHelper.UP or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

    private val onSwipeCallBack = callBack
    private val layoutManager = manager

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipeCallBack.onSwiped(viewHolder, direction)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val maxDistance = recyclerView.width / 2f
        val distance = sqrt(dX * dX + dY * dY)
        var fraction = distance / maxDistance

        if (fraction > 1) {
            fraction = 1f
        }

        when {
            dX < 0 -> {
                //to left
                viewHolder.itemView.rotation = (abs(dX) / maxDistance) * 360 * -1 * 0.2f
            }
            dX > 0 -> {
                //to right
                viewHolder.itemView.rotation = (abs(dX) / maxDistance) * 360 * 0.2f
            }
            else -> {
                viewHolder.itemView.rotation = 0f
            }
        }

        val itemCount = recyclerView.childCount
        for (i in 0 until itemCount) {
            val view = recyclerView.getChildAt(i)
            val level = itemCount - i - 1
            if (level > 0) {
                if (level < layoutManager.maxShowCount - 1) {
                    view.translationY = layoutManager.transYGap * level - fraction * layoutManager.transYGap
                    view.scaleX = 1 - layoutManager.scaleGap * level + fraction * layoutManager.scaleGap
                    view.scaleY = 1 - layoutManager.scaleGap * level + fraction * layoutManager.scaleGap
                }
            }
        }
    }
}

interface OnSwipeCallBack {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
}