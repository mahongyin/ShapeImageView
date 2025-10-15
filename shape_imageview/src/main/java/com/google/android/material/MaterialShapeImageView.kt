package com.google.android.material

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import com.google.android.material.edge.MyTriangleEdgeTreatment
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.mhy.shape_imageview.R
import kotlin.math.abs

/**
 * Created By Mahongyin
 * Date    2020/8/5 11:50
 * 给shapeAppearance设置的 style，并不是[com.google.android.material.imageview.ShapeableImageView]自己来处理的，
 * 而是由[com.google.android.material.shape.ShapeAppearanceModel]来构建的，然后又交给[MaterialShapeDrawable]来绘制的。
 * 有各种边和角的属性
 * [MaterialShapeUtils.createDefaultCornerTreatment] 是创建默认角的处理方式,material包里还提供了其他的
 * [MaterialShapeUtils.createDefaultEdgeTreatment] 是创建默认边的处理方式,material包里还提供了其他的
 * 边和角，是可以自定义的
 * issues:
 * 指定strokeWidth描边的时候，其描边会被覆盖掉一半，如strokeWidth=4dp，上下左右会被覆盖，实际的效果是只有2dp被显示(画笔一半)
 * 描边问题处理方法:
 *         app:strokeWidth="4dp"
 *         android:padding="2dp"
 * 除了可以设置描边之外，还可以设置背景、阴影等其他属性
 * app:elevation设置阴影的高度,
 * android:backgroundTint设置阴影的颜色
 * app:contentPadding[left/top/r/b]
 * 使用:
 *             <shapeview.ShapeImageView
 *             android:id="@+id/photo"
 *             android:layout_width="@dimen/common_sw_160dp"
 *             android:layout_height="@dimen/common_sw_0dp"
 *             android:scaleType="centerCrop"
 *             app:imgPlaceholder="@{com.cctv.cctv5ultimate.common.R.drawable.common_default_4_3}"
 *             app:layout_constraintBottom_toBottomOf="@id/bg"
 *             app:layout_constraintEnd_toEndOf="@id/bg"
 *             app:layout_constraintTop_toTopOf="@id/bg"
 *             app:loadImage="@{m.photo.thumb}"
 *             app:shapeImg_corner_BottomRight="@dimen/common_sw_6dp"
 *             app:shapeImg_corner_TopRight="@dimen/common_sw_6dp"
 *             app:shapeImg_edge_LeftTriangle="@dimen/common_sw_11dp"
 *             tools:background="@color/common_ff909abd" />
 */

@SuppressLint("RestrictedApi")
class MaterialShapeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ShapeableImageView(context, attrs, defStyleAttr) {
    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.MaterialShapeImageView, defStyleAttr,
            com.google.android.material.R.style.Widget_MaterialComponents_ShapeableImageView)
        try {
            val shapeType: Int =
                a.getInt(R.styleable.MaterialShapeImageView_shapeImg_Type, CornerFamily.ROUNDED)
            val shapeTypeTopLeft: Int =
                a.getInt(R.styleable.MaterialShapeImageView_shapeImg_Type_TopLeft, shapeType)
            val shapeTypeTopRight: Int =
                a.getInt(R.styleable.MaterialShapeImageView_shapeImg_Type_TopRight, shapeType)
            val shapeTypeBottomRight: Int =
                a.getInt(R.styleable.MaterialShapeImageView_shapeImg_Type_BottomRight, shapeType)
            val shapeTypeBottomLeft: Int =
                a.getInt(R.styleable.MaterialShapeImageView_shapeImg_Type_BottomLeft, shapeType)

            val allTriangleEdge =
                a.getDimension(R.styleable.MaterialShapeImageView_shapeImg_edge_AllTriangle, 0f)
            val topTriangleEdge =
                a.getDimension(R.styleable.MaterialShapeImageView_shapeImg_edge_TopTriangle, 0f)
            val leftTriangleEdge =
                a.getDimension(R.styleable.MaterialShapeImageView_shapeImg_edge_LeftTriangle, 0f)
            val rightTriangleEdge =
                a.getDimension(R.styleable.MaterialShapeImageView_shapeImg_edge_RightTriangle, 0f)
            val bottomTriangleEdge =
                a.getDimension(R.styleable.MaterialShapeImageView_shapeImg_edge_BottomTriangle, 0f)

            val builder = ShapeAppearanceModel.builder()
            builder.apply {
                if (allTriangleEdge != 0f) {
                    setAllEdges(
                        MyTriangleEdgeTreatment(
                            abs(
                                allTriangleEdge
                            ), allTriangleEdge > 0
                        )
                    )
                }
                if (topTriangleEdge != 0f) {
                    setTopEdge(
                        MyTriangleEdgeTreatment(
                            abs(
                                topTriangleEdge
                            ), topTriangleEdge > 0
                        )
                    )
                }
                if (rightTriangleEdge != 0f) {
                    setRightEdge(
                        MyTriangleEdgeTreatment(
                            abs(
                                rightTriangleEdge
                            ), rightTriangleEdge > 0
                        )
                    )
                }
                if (leftTriangleEdge != 0f) {
                    setLeftEdge(
                        MyTriangleEdgeTreatment(
                            abs(
                                leftTriangleEdge
                            ), leftTriangleEdge > 0
                        )
                    )
                }
                if (bottomTriangleEdge != 0f) {
                    setBottomEdge(
                        MyTriangleEdgeTreatment(
                            abs(
                                bottomTriangleEdge
                            ), bottomTriangleEdge > 0
                        )
                    )
                }
            }
            if (a.hasValue(R.styleable.MaterialShapeImageView_shapeImg_corner_All)) {
                val cornerSize = getCornerSize(
                    a, R.styleable.MaterialShapeImageView_shapeImg_corner_All,
                    AbsoluteCornerSize(0F)
                )
                builder.setAllCornerSizes(cornerSize)
            } else {
                val cornerSizeTopLeft =
                    getCornerSize(
                        a,
                        R.styleable.MaterialShapeImageView_shapeImg_corner_TopLeft,
                        AbsoluteCornerSize(0F)
                    )
                val cornerSizeTopRight =
                    getCornerSize(
                        a,
                        R.styleable.MaterialShapeImageView_shapeImg_corner_TopRight,
                        AbsoluteCornerSize(0F)
                    )
                val cornerSizeBottomRight =
                    getCornerSize(
                        a,
                        R.styleable.MaterialShapeImageView_shapeImg_corner_BottomRight,
                        AbsoluteCornerSize(0F)
                    )
                val cornerSizeBottomLeft =
                    getCornerSize(
                        a,
                        R.styleable.MaterialShapeImageView_shapeImg_corner_BottomLeft,
                        AbsoluteCornerSize(0F)
                    )
                builder.setTopLeftCorner(shapeTypeTopLeft, cornerSizeTopLeft)
                    .setTopRightCorner(shapeTypeTopRight, cornerSizeTopRight)
                    .setBottomRightCorner(shapeTypeBottomRight, cornerSizeBottomRight)
                    .setBottomLeftCorner(shapeTypeBottomLeft, cornerSizeBottomLeft)
            }
            shapeAppearanceModel = builder.build()
        } finally {
            a.recycle()
        }
    }

    private fun getCornerSize(a: TypedArray, index: Int, defaultValue: CornerSize): CornerSize {
        val value = a.peekValue(index) ?: return defaultValue
        return if (value.type == TypedValue.TYPE_DIMENSION) {//TYPE_REFERENCE
            //最终我们可能希望将其更改为调用 getDimension()，因为角尺寸支持浮点数。
            AbsoluteCornerSize(
                TypedValue.complexToDimension(value.data, a.resources.displayMetrics)
            )
        } else if (value.type == TypedValue.TYPE_FRACTION) {
            RelativeCornerSize(value.getFraction(1.0f, 1.0f))
        } else {
            defaultValue
        }
    }

}