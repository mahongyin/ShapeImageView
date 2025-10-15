package com.mhy.shapeview.drawable

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Property

//imageview.setImageDrawable(CircleDrawable())
class CircleDrawable : Drawable(), Animatable {
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mValueAnimator: ValueAnimator? = null
    private var mRadius: Float = 0f //扩散半径

    companion object {
        const val STROKE_WIDTH = 5f
        const val DEFAULT_DURATION = 1200L
        const val DEFAULT_DELAY_TIME = 0L
    }

    //绘制的矩形框
    private var mRect = RectF()

    //动画启动延迟时间
    private var mStartDelay: Long = DEFAULT_DELAY_TIME

    private var mRadiusProperty: Property<CircleDrawable, Int> =
        object : Property<CircleDrawable, Int>(
            Int::class.java, "radius"
        ) {
            override fun set(circleDrawable: CircleDrawable, value: Int) {
                circleDrawable.setRadius(value)
            }

            override fun get(circleDrawable: CircleDrawable): Int {
                return circleDrawable.getRadius().toInt()
            }
        }

    fun getRadius(): Float {
        return mRadius
    }

    fun setRadius(radius: Int) {
        mRadius = radius.toFloat()
    }

    init {
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = STROKE_WIDTH
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        mRect.set(clipSquare(bounds))
        if (isRunning) {
            stop()
        }
        val maxRadius = ((mRect.right - mRect.left) / 2).toInt()
        val radiusHolder = PropertyValuesHolder.ofInt(mRadiusProperty, 0, maxRadius)
        val alphaHolder = PropertyValuesHolder.ofInt("alpha", 255, 0)

        mValueAnimator = ObjectAnimator.ofPropertyValuesHolder(this, radiusHolder, alphaHolder)
        mValueAnimator?.startDelay = mStartDelay
        mValueAnimator?.duration = DEFAULT_DURATION
        mValueAnimator?.addUpdateListener {
            invalidateSelf()
        }
        mValueAnimator?.repeatMode = ValueAnimator.RESTART
        mValueAnimator?.repeatCount = ValueAnimator.INFINITE
        start()
    }

    /**
     * 裁剪为正方形
     */
    private fun clipSquare(bounds: Rect): Rect {
        val min = Math.min(bounds.width(), bounds.height())
        val cx = bounds.centerX()
        val cy = bounds.centerY()
        val r = min / 2
        return Rect(cx - r, cy - r, cx + r, cy + r)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(mRect.centerX(), mRect.centerY(), mRadius, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun start() {
        mValueAnimator?.start()
    }

    override fun stop() {
        mValueAnimator?.end()
    }

    override fun isRunning(): Boolean {
        return mValueAnimator?.isRunning == true
    }
}