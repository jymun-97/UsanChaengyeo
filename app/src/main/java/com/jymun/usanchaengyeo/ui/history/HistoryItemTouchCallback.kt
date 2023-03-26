package com.jymun.usanchaengyeo.ui.history

import android.graphics.Canvas
import android.util.TypedValue
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.jymun.usanchaengyeo.R
import com.jymun.usanchaengyeo.data.model.history.History
import com.jymun.usanchaengyeo.util.resources.ResourcesProvider
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class HistoryItemTouchCallback(
    private val resourcesProvider: ResourcesProvider,
    private val onLeftSwiped: (history: History) -> Unit,
    private val onRightSwiped: (history: History) -> Unit
) : SimpleCallback(0, LEFT or RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val history = (viewHolder as? HistoryViewHolder)?.history ?: return

        if (direction == LEFT) onLeftSwiped(history)
        else onRightSwiped(history)
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
        RecyclerViewSwipeDecorator
            .Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftActionIcon(R.drawable.ic_delete)
            .addSwipeLeftBackgroundColor(resourcesProvider.getColor(R.color.transparent_red))
            .addSwipeLeftLabel(resourcesProvider.getString(R.string.delete))
            .setSwipeLeftLabelColor(resourcesProvider.getColor(R.color.white))
            .addSwipeRightActionIcon(R.drawable.ic_pin)
            .addSwipeRightBackgroundColor(resourcesProvider.getColor(R.color.transparent_green))
            .addSwipeRightLabel(resourcesProvider.getString(R.string.pin))
            .setSwipeRightLabelColor(resourcesProvider.getColor(R.color.white))
            .addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 12)
            .addPadding(TypedValue.COMPLEX_UNIT_DIP, 0F, 14F, 0F)
            .create()
            .decorate()

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}