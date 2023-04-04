package com.jymun.usanchaengyeo.ui.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.jymun.usanchaengyeo.R

class BarView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    private lateinit var barPaint: Paint
    private lateinit var barStrokePaint: Paint
    private lateinit var barRect: RectF

    var barValue = 0
        set(value) {
            field = value

            initPaints()
            barHeight =
                barMinHeight + value.toFloat() / (barMaxValue - barMinValue) * (barMaxHeight - barMinHeight)
            barRect = RectF(0f, 0f, width.toFloat(), barHeight).apply {
                inset(barStrokeWidth / 2, barStrokeWidth / 2)
            }

            invalidate()
        }

    var barColor = 0
    var barMinValue = 0
    var barMaxValue = 100
    var barRadius = 0f
    var barMinHeight = 0f
    var barMaxHeight = 0f
    var barHeight = 0f
    var barStrokeWidth = 0f

    init {
        initAttrs(attributeSet)
        initPaints()
    }

    private fun initAttrs(attr: AttributeSet) = context.theme.obtainStyledAttributes(
        attr, R.styleable.BarView, 0, 0
    ).apply {
        try {
            barValue = getInteger(
                R.styleable.BarView_bar_value,
                0
            )
            barMinValue = getInteger(
                R.styleable.BarView_bar_min_value,
                context.resources.getInteger(R.integer.forecast_min_value)
            )
            barMaxValue = getInteger(
                R.styleable.BarView_bar_max_value,
                context.resources.getInteger(R.integer.forecast_max_value)
            )
            barRadius = getDimension(
                R.styleable.BarView_bar_radius,
                context.resources.getDimension(R.dimen.bar_radius)
            )
            barMinHeight = getDimension(
                R.styleable.BarView_bar_min_height,
                context.resources.getDimension(R.dimen.bar_min_height)
            )
            barMaxHeight = getDimension(
                R.styleable.BarView_bar_max_height,
                context.resources.getDimension(R.dimen.bar_max_height)
            )
            barStrokeWidth = getDimension(
                R.styleable.BarView_bar_stroke_width,
                context.resources.getDimension(R.dimen.bar_stroke_width)
            )
        } finally {
            recycle()
        }
    }

    private fun initPaints() {
        val starts = context.resources.getIntArray(R.array.forecast_info_starts)
        val colors = context.resources.getIntArray(R.array.forecast_colors)

        barColor = colors[starts.indexOfLast { it <= barValue }]
        barPaint = Paint().apply {
            style = Paint.Style.FILL_AND_STROKE
            color = barColor
            alpha = 128
        }
        barStrokePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = barColor
            strokeWidth = barStrokeWidth
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRoundRect(barRect, barRadius, barRadius, barPaint)
        canvas?.drawRoundRect(barRect, barRadius, barRadius, barStrokePaint)
    }

    companion object {
        @BindingAdapter("app:bar_value")
        @JvmStatic
        fun setBarValue(view: BarView, value: Int) {
            view.barValue = value
            view.updateLayoutParams {
                height = view.barHeight.toInt()
            }
        }
    }
}