package com.abhay.visionassist

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results = listOf<BoundingBox>()
    private var inferenceTime: Long = 0L
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()
    private var timeTextPaint = Paint()
    private var timeBackgroundPaint = Paint()

    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        results = listOf()
        inferenceTime = 0L
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        timeTextPaint.reset()
        timeBackgroundPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        timeBackgroundPaint.color = Color.BLACK
        timeBackgroundPaint.alpha = 150 // semi-transparent
        timeBackgroundPaint.style = Paint.Style.FILL

        timeTextPaint.color = Color.WHITE
        timeTextPaint.style = Paint.Style.FILL
        timeTextPaint.textSize = 60f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        results.forEach {
            val left = it.x1 * width
            val top = it.y1 * height
            val right = it.x2 * width
            val bottom = it.y2 * height

            canvas.drawRect(left, top, right, bottom, boxPaint)
            val drawableText = it.clsName

            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)

        }

        if (inferenceTime > 0) {
            val timeText = "Inference Time: ${inferenceTime}ms"
            timeTextPaint.getTextBounds(timeText, 0, timeText.length, bounds)

            val padding = 20
            val startX = 20f
            val startY = 80f // Draw near the top left

            canvas.drawRect(
                startX - padding,
                startY - bounds.height() - padding,
                startX + bounds.width() + padding,
                startY + padding,
                timeBackgroundPaint
            )
            canvas.drawText(timeText, startX, startY, timeTextPaint)
        }
    }

    fun setResults(boundingBoxes: List<BoundingBox>, inferenceTime: Long = 0L) {
        results = boundingBoxes
        this.inferenceTime = inferenceTime
        invalidate()
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}
