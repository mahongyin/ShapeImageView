package com.mhy.shapeview.material

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.annotation.ColorRes
import com.mhy.shapeview.drawable.Utils.dpToPx
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerTreatment
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.OffsetEdgeTreatment
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.ShapePath
import com.google.android.material.shape.TriangleEdgeTreatment
import com.mhy.shapeview.drawable.Utils

/** https://blog.csdn.net/chuyouyinghe/article/details/126042315
 * Created By Mahongyin
 * Date    2022/8/10 22:08
 *  <com.google.android.material.card.MaterialCardView
 *
 * 圆角处理 RoundedCornerTreatment
 * 切角处理 CutCornerTreatment
 * 三角边处理 TriangleEdgeTreatment
 * 偏移边缘处理 OffsetEdgeTreatment
 * 标记边缘处理 MarkerEdgeTreatment
 */
@SuppressLint("RestrictedApi")
object ShapeDrawable {
    // 代码设置 角和边
    fun getShape6(context: Context, @ColorRes color: Int, stroke_color: Int): Drawable {
        val shapeAppearanceModel2 = ShapeAppearanceModel.builder().apply {
            setAllCorners(RoundedCornerTreatment())
            setAllCornerSizes(50f)
            setAllEdges(TriangleEdgeTreatment(50f, false))
        }.build()
        val drawable2 = MaterialShapeDrawable(shapeAppearanceModel2).apply {
            setTint(ContextCompat.getColor(context, color))
            paintStyle = Paint.Style.FILL_AND_STROKE
            strokeWidth = 50f
            strokeColor = ContextCompat.getColorStateList(context, stroke_color)
        }
        return drawable2
    }

    // 代码设置 聊天框效果
    fun getShape2(context: Context, @ColorRes color: Int): Drawable {
        val shapeAppearanceModel3 = ShapeAppearanceModel.builder().apply {
            setAllCorners(RoundedCornerTreatment())
            setAllCornerSizes(20f)
            setRightEdge(object : TriangleEdgeTreatment(20f, false) {
                // center 位置 ， interpolation 角的大小
                override fun getEdgePath(length: Float,
                    center: Float,
                    interpolation: Float,
                    shapePath: ShapePath) {
                    super.getEdgePath(length, 35f, interpolation, shapePath)
                }
            })
        }.build()
        val drawable3 = MaterialShapeDrawable(shapeAppearanceModel3).apply {
            setTint(ContextCompat.getColor(context, color))
            paintStyle = Paint.Style.FILL
        }
//        (mBinding.text3.parent as ViewGroup).clipChildren = false // 不限制子view在其范围内
//        mBinding.text3.setTextColor(Color.WHITE)
//        mBinding.text3.background = drawable3
        return drawable3
    }

    //气泡
    fun getShape9() {
        val shapeModel = ShapeAppearanceModel.builder().setAllCornerSizes(6F)
            .setBottomEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(6F, false), 30F)).build()
        val backgroundDrawable = MaterialShapeDrawable(shapeModel).apply {
            setTint(Color.parseColor("#FA4B05"))
            paintStyle = Paint.Style.FILL
        }
//    (textView04.parent as ViewGroup).clipChildren = false
//    textView04.background = backgroundDrawable

    }

    //内三角
    fun getShape3(): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(20f).setAllEdges(TriangleEdgeTreatment(18f, true)).build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL_AND_STROKE
            strokeWidth = 4f
        }
        return backgroundDrawable
    }

    //两边内扣半圆
    fun getSape5(context: Context, title_layout: ViewGroup, @ColorRes color: Int) {
        val radius = 12f
        val titleShapeModel =
            ShapeAppearanceModel().toBuilder().setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(ConcaveRoundedCornerTreatment())
                .setBottomLeftCornerSize(radius)
                .setBottomRightCorner(ConcaveRoundedCornerTreatment())
                .setBottomRightCornerSize(radius).build()
        val titleBackground = MaterialShapeDrawable(titleShapeModel)
        titleBackground.setStroke(1f, ContextCompat.getColor(context, color))

        ViewCompat.setBackground(title_layout, titleBackground)
    }

    //阴影
    fun getShape4(context: Context) {
        val shapePathModel = ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(32f).build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#05bebebe"))
            paintStyle = Paint.Style.FILL
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(context)
            shadowRadius = 32
            setShadowColor(Color.parseColor("#D2D2D2"))
            elevation = 10f
            shadowVerticalOffset = 4
        }
        // (test1.parent as? ViewGroup)?.clipChildren = false
    }

    //阴影
    fun getShape8(context: Context) {
        val shapeModel = ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(16F).build()
        val backgroundDrawable = MaterialShapeDrawable(shapeModel).apply {
            setTint(Color.GRAY)
            paintStyle = Paint.Style.FILL

            //绘制阴影
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(context)
            setShadowColor(Color.RED)
            elevation = 10F
        }
//        (textView08.parent as ViewGroup).clipChildren = false
//        textView08.background = backgroundDrawable

    }


    fun getShape7(context: Context): Drawable {
        val shapePathModel = ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(24f)

            //.setAllEdges(TriangleEdgeTreatment(dpToPx(context,10), true))
            .setBottomEdge(
                OffsetEdgeTreatment(//偏移
                    TriangleEdgeTreatment(dpToPx(context,6), false),
                    dpToPx(context,30)
                )
            )//气泡
            .setRightEdge(object : TriangleEdgeTreatment(dpToPx(context,12), false) {
                override fun getEdgePath(length: Float,
                    center: Float,
                    interpolation: Float,
                    shapePath: ShapePath) {
                    super.getEdgePath(length, 20f, interpolation, shapePath)
                }
            }).build()
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#bebebe"))
            paintStyle = Paint.Style.FILL
//            paintStyle = Paint.Style.FILL_AND_STROKE
//            strokeWidth = dp2px(2F)
//            strokeColor = ContextCompat.getColorStateList(context, R.color.red)
        }
//        (test1.parent as? ViewGroup)?.clipChildren = false
//        test1.background = backgroundDrawable
        return backgroundDrawable
    }

    /**
     * 自定义角处理
     */
    fun cardViewShape2(cardView: com.google.android.material.card.MaterialCardView) {
        cardView.shapeAppearanceModel =
            cardView.shapeAppearanceModel.toBuilder().setTopLeftCorner(CustomCornerTreatment())
                .setBottomLeftCorner(CornerFamily.ROUNDED, 20f)
                .setBottomRightCorner(CornerFamily.ROUNDED, 20f).build()
    }

    fun cardViewShape(cardView: com.google.android.material.card.MaterialCardView) {
        cardView.shapeAppearanceModel = cardView.shapeAppearanceModel.toBuilder()
            .setBottomRightCorner(InnerCorner2Treatment(50f, 90f, 30f))
            .setBottomRightCornerSize(40f).setTopLeftCorner(InnerCorner1Treatment())
            .setTopLeftCornerSize(60f).build()
    }

    fun imageShape(image: ShapeableImageView) {
        image.shapeAppearanceModel = image.shapeAppearanceModel.toBuilder()
            .setBottomRightCorner(InnerCorner2Treatment(50f, 90f, 30f))
            .setBottomRightCornerSize(40f).setTopLeftCorner(InnerCorner1Treatment())
            .setTopLeftCornerSize(60f).build()
    }

    fun shadowShape(context: Context, view: View) {
        val shapePathModel = ShapeAppearanceModel.builder()
            .setBottomRightCorner(InnerCorner2Treatment(50f, 90f, 30f))
            .setBottomRightCornerSize(40f).setTopLeftCorner(InnerCorner1Treatment())
            .setTopLeftCornerSize(60f).build()
        // 使用MaterialShapeDrawable来实现阴影效果
        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(Color.parseColor("#05bebebe"))
            paintStyle = Paint.Style.FILL
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
            initializeElevationOverlay(context)
            shadowRadius = 32
            setShadowColor(Color.parseColor("#D2D2D2"))
            shadowVerticalOffset = 4
        }
//        (test1.parent as? ViewGroup)?.clipChildren = false
//        test1.background = backgroundDrawable
        ViewCompat.setBackground(view, backgroundDrawable)
    }

    fun outLineShape(view: View) {
        view.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRect(0, 0, view.width, view.height)
            }
        }
    }

    fun getGradientShape(startColor: Int, endColor: Int): Drawable {
        val drawable = GradientDrawable(//渐变色
            GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(startColor, endColor))
        return drawable
    }


    //TODO ShapeTextView 例子
    fun shapeTest(view: ShapeTextView) {
        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 40)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(CutCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 50)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(RoundedCornerTreatment())
                .setTopLeftCornerSize(dpToPx(view.context, 80))
                .setTopRightCorner(RoundedCornerTreatment())
                .setTopRightCornerSize(dpToPx(view.context, 80))
                .setBottomLeftCorner(RoundedCornerTreatment())
                .setBottomLeftCornerSize(dpToPx(view.context, 80)).build()

        view.shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setRightEdge(TriangleEdgeTreatment(dpToPx(view.context, 100), true)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(RoundedCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 50)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 100)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(CutCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 100)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setTopLeftCorner(RoundedCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 50))
                .setTopEdge(TriangleEdgeTreatment(dpToPx(view.context, 24), false))
                .setBottomEdge(TriangleEdgeTreatment(dpToPx(view.context, 24), true)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(RoundedCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 24))
                .setAllEdges(ArgEdgeTreatment(dpToPx(view.context, 24), true)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(InnerRoundCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 24))
                .setAllEdges(ArgEdgeTreatment(dpToPx(view.context, 24), false)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(InnerCutCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 60)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(InnerRoundCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 60))
//            .setAllEdges(QuadEdgeTreatment(40.dp()))
                .build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(ExtraRoundCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 24))
                .setAllEdges(ArgEdgeTreatment(dpToPx(view.context, 24), false)).build()

        view.shapeAppearanceModel =
            ShapeAppearanceModel.builder().setAllCorners(InnerRoundCornerTreatment())
                .setAllCornerSizes(dpToPx(view.context, 24))
                .setAllEdges(ArgEdgeTreatment(dpToPx(view.context, 24), true)).build()
    }

    class InnerCutCornerTreatment : CornerTreatment() {
        override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
            val radius = size * f
            shapePath.reset(0f, radius, 180f, 180 - angle)
            shapePath.lineTo(radius, radius)
            shapePath.lineTo(radius, 0f)
        }
    }

    class InnerRoundCornerTreatment : CornerTreatment() {
        override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
            val radius = size * f
            shapePath.reset(0f, radius, 180f, 180 - angle)
            shapePath.addArc(-radius, -radius, radius, radius, angle, -90f)
        }
    }

    class ExtraRoundCornerTreatment : CornerTreatment() {
        override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
            val radius = size * f
            shapePath.reset(0f, radius, 180f, 180 - angle)
            shapePath.addArc(-radius, -radius, radius, radius, angle, 270f)
        }
    }

    class ArgEdgeTreatment(val size: Float, val inside: Boolean) : EdgeTreatment() {
        override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
            val radius = size * f
            shapePath.lineTo(center - radius, 0f)
            shapePath.addArc(center - radius,
                -radius,
                center + radius,
                radius,
                180f,
                if (inside) -180f else 180f)
            shapePath.lineTo(length, 0f)
        }
    }

    class QuadEdgeTreatment(val size: Float) : EdgeTreatment() {
        override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
            shapePath.quadToPoint(center, size * f, length, 0f)
        }
    }
}