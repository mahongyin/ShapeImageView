package com.cctv.cctv5ultimate.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.Shader
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created By Mahongyin
 * Date    2022/9/6 17:39
 * 渐变色背景图
 */

class GradientView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {

    @ColorInt private var colorStart = 0
    @ColorInt private var colorEnd = 0

    private val paint = Paint()
    private val gradientPaint = Paint() //不要锯齿抖动否则空白显示黑色

    companion object {
        const val NORMALCOLOR = 0
    }

    @DrawableRes
    private var drawableResource = android.R.drawable.ic_delete

    init {
        paint.isAntiAlias = true
        paint.isDither = true
    }

    /**
     * color设置0[NORMALCOLOR]就是原色，不做处理
     */
    fun setGradientColors(@ColorInt startColor: Int,@ColorInt endColor: Int,
        @DrawableRes resId: Int = android.R.drawable.ic_delete) {
        colorStart = startColor
        colorEnd = endColor
        drawableResource = resId
        invalidate()
    }

    private fun addGradient(originalBitmap: Bitmap,
        dcolors: IntArray?): Bitmap { //给originalBitmap着渐变色
        var colors = dcolors
        if (colors == null || colors.size == 0) { //默认色处理
            colors = intArrayOf(Color.parseColor("#ff9900"), Color.parseColor("#ff9900"))
        } else if (colors.size == 1) { //单色处理
            val newColor = intArrayOf(colors[0], colors[0])
            colors = newColor
        }
        val width = originalBitmap.width
        val height = originalBitmap.height
        val canvas = Canvas(originalBitmap) //Canvas中Bitmap是用来保存像素，相当于画纸
        val shader = LinearGradient(0f, 0f, width.toFloat(), 0f, colors, null,
            Shader.TileMode.CLAMP) //shader:着色器，线性着色器设置渐变从左上坐标到右下坐标
        gradientPaint.shader = shader //设置着色器
        gradientPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) //设置图像混合模式
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), gradientPaint)
        return originalBitmap
    }

    private fun getGradientBitmap(bitMap: Bitmap, colors: IntArray?): Bitmap {
        //android不允许直接修改res里面的图片，所以要用copy方法
        val bitmap = bitMap.copy(Bitmap.Config.ARGB_8888, true)
        addGradient(bitmap, colors)
        return bitmap
    }

    override fun draw(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(resources, drawableResource)
        val rect = Rect()
        rect.set(0, 0, width, height)
        if (colorStart != NORMALCOLOR && colorEnd != NORMALCOLOR) {
            val bitmap2 = getGradientBitmap(bitmap, intArrayOf(colorStart, colorEnd))
            canvas.drawBitmap(zoomImage(bitmap2, width, height), rect, rect, paint)
        } else {
            canvas.drawBitmap(zoomImage(bitmap, width, height), rect, rect, paint)
        }
        //后绘制文字
        super.draw(canvas)
    }

    private fun zoomImage(bgimage: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        // 获取这个图片的宽和高
        val width = bgimage.width.toFloat()
        val height = bgimage.height.toFloat()
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = newWidth / width
        val scaleHeight = newHeight / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bgimage, 0, 0, width.toInt(), height.toInt(), matrix, true)
    }


}