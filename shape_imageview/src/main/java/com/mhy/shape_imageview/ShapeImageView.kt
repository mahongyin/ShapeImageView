package com.mhy.shape_imageview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Color.red
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.AttributeSet
import android.util.Half.toFloat
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.*

/**
 * Created By Mahongyin
 * Date    2022/8/5 11:50
 * 给shapeAppearance设置的 style，并不是ShapeableImageView自己来处理的，
 * 而是由ShapeAppearanceModel来构建的，然后又交给MaterialShapeDrawable来绘制的。
 * 有各种边和角的属性
 * MaterialShapeUtils.createDefaultCornerTreatment() 创建默认角的处理方式
 * MaterialShapeUtils.createDefaultEdgeTreatment() 创建默认边的处理方式
 * 边和角，是可以自定义的
 *
 * 指定strokeWidth描边的时候，其描边会被覆盖掉一半，如strokeWidth=4dp，上下左右会被覆盖，实际的效果是只有2dp被显示(画笔一半)
 * 描边问题处理方法:
 *         app:strokeWidth="4dp"
 *         android:padding="2dp"
 * 除了可以设置描边之外，还可以设置背景、阴影等其他属性
 */
/*代码设置
imageView?.shapeAppearanceModel = ShapeAppearanceModel.builder()
.setAllCorners(CornerFamily.ROUNDED,20f)
.setTopLeftCorner(CornerFamily.CUT,RelativeCornerSize(0.3f))
.setTopRightCorner(CornerFamily.CUT,RelativeCornerSize(0.3f))
.setBottomRightCorner(CornerFamily.CUT,RelativeCornerSize(0.3f))
.setBottomLeftCorner(CornerFamily.CUT,RelativeCornerSize(0.3f))
.setAllCornerSizes(ShapeAppearanceModel.PILL)
.setTopLeftCornerSize(20f)
.setTopRightCornerSize(RelativeCornerSize(0.5f))
.setBottomLeftCornerSize(10f)
.setBottomRightCornerSize(AbsoluteCornerSize(30f))
.build()*/

class ShapeImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ShapeableImageView(context, attrs) {

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShapeImageView)
        try {
            val shapeType: Int =
                a.getInt(R.styleable.ShapeImageView_shape_All, CornerFamily.ROUNDED)
            val shapeTypeTopLeft: Int =
                a.getInt(R.styleable.ShapeImageView_shape_TopLeft, shapeType)
            val shapeTypeTopRight: Int =
                a.getInt(R.styleable.ShapeImageView_shape_TopRight, shapeType)
            val shapeTypeBottomRight: Int =
                a.getInt(R.styleable.ShapeImageView_shape_BottomRight, shapeType)
            val shapeTypeBottomLeft: Int =
                a.getInt(R.styleable.ShapeImageView_shape_BottomLeft, shapeType)

            val cornerSize = getCornerSize(a, R.styleable.ShapeImageView_shape_corner_All,
                AbsoluteCornerSize(0F))
            val cornerSizeTopLeft =
                getCornerSize(a, R.styleable.ShapeImageView_shape_corner_TopLeft, cornerSize)
            val cornerSizeTopRight =
                getCornerSize(a, R.styleable.ShapeImageView_shape_corner_TopRight, cornerSize)
            val cornerSizeBottomRight =
                getCornerSize(a, R.styleable.ShapeImageView_shape_corner_BottomRight, cornerSize)
            val cornerSizeBottomLeft =
                getCornerSize(a, R.styleable.ShapeImageView_shape_corner_BottomLeft, cornerSize)

            shapeAppearanceModel = ShapeAppearanceModel.builder()
                    .setTopLeftCorner(shapeTypeTopLeft, cornerSizeTopLeft)
                    .setTopRightCorner(shapeTypeTopRight, cornerSizeTopRight)
                    .setBottomRightCorner(shapeTypeBottomRight, cornerSizeBottomRight)
                    .setBottomLeftCorner(shapeTypeBottomLeft, cornerSizeBottomLeft).build()
        } finally {
            a.recycle()
        }
    }

    private fun getCornerSize(a: TypedArray, index: Int, defaultValue: CornerSize): CornerSize {
        val value = a.peekValue(index) ?: return defaultValue
        return if (value.type == TypedValue.TYPE_DIMENSION) {
            //最终我们可能希望将其更改为调用 getDimension()，因为角尺寸支持浮点数。
            AbsoluteCornerSize(
                TypedValue.complexToDimensionPixelSize(value.data, a.resources.displayMetrics)
                    .toFloat())
        } else if (value.type == TypedValue.TYPE_FRACTION) {
            RelativeCornerSize(value.getFraction(1.0f, 1.0f))
        } else {
            defaultValue
        }
    }

}