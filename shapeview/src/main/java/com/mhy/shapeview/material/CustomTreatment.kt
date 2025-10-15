package com.mhy.shapeview.material

import com.google.android.material.shape.CornerTreatment
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

/**
 * 自定义角处理
 */
class CustomCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, interpolation: Float,
        radius: Float) {
        val interpolatedRadius = radius * interpolation
        shapePath.reset(0f, -radius * interpolation, 270f, 270 - angle)
        shapePath.addArc(0f, -2 * interpolatedRadius, 2 * interpolatedRadius, 0f, 180f, -angle)
    }
}

//两边内扣半圆
class ConcaveRoundedCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, interpolation: Float,
        radius: Float) {
        val interpolatedRadius = radius * interpolation
        shapePath.reset(0f, interpolatedRadius, ANGLE_LEFT, ANGLE_LEFT - angle)
        shapePath.addArc(-interpolatedRadius, -interpolatedRadius, interpolatedRadius,
            interpolatedRadius, ANGLE_BOTTOM, -angle)
    }

    companion object {
        const val ANGLE_LEFT = 180f
        const val ANGLE_BOTTOM = 90f
    }
}

//内切角
class InnerCutCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        shapePath.reset(0f, radius, 180f, 180 - angle)
        shapePath.lineTo(radius, radius)
        shapePath.lineTo(radius, 0f)
    }
}

//内圆角
class InnerRoundCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        shapePath.reset(0f, radius, 180f, 180 - angle)
        shapePath.addArc(-radius, -radius, radius, radius, angle, -90f)
    }
}


//外圆角
class ExtraRoundCornerTreatment : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        shapePath.reset(0f, radius, 180f, 180 - angle)
        shapePath.addArc(-radius, -radius, radius, radius, angle, 270f)
    }
}

//内外边鼓包
class ArgEdgeTreatment(val size: Float, val inside: Boolean) : EdgeTreatment() {
    override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
        val radius = size * f
        shapePath.lineTo(center - radius, 0f)
        shapePath.addArc(center - radius, -radius, center + radius, radius, 180f,
            if (inside) -180f else 180f)
        shapePath.lineTo(length, 0f)
    }
}

//边弯曲
class QuadEdgeTreatment(val size: Float) : EdgeTreatment() {
    override fun getEdgePath(length: Float, center: Float, f: Float, shapePath: ShapePath) {
        shapePath.quadToPoint(center, size * f, length, 0f)
    }
}

class InnerCorner1Treatment() : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius = size * f
        val radius1 = 18f
        val radius2 = 32f
        shapePath.reset(0f, radius + radius1, 180f, 180 - angle)
        shapePath.addArc(0f, radius, radius1, radius + radius1, 180f, 90f)
        shapePath.lineTo(radius - radius2, radius)
        shapePath.addArc(radius - radius2, radius - radius2, radius, radius, angle, -90f)
        shapePath.lineTo(radius, radius1)
        shapePath.addArc(radius, 0f, radius + radius1, radius1, 180f, 90f)
    }
}

/**
 * x,y 缺口横纵长度需要大于setCornerSize
 * radius1 2小角
 * radius2 角的反角 从 setCornerSize获取
 */
class InnerCorner2Treatment(private val y: Float, private val x: Float, private val radius1: Float) : CornerTreatment() {
    override fun getCornerPath(shapePath: ShapePath, angle: Float, f: Float, size: Float) {
        val radius2 = size * f
        shapePath.reset(0f, radius1 + y, 180f, 180 - angle)
        shapePath.addArc(0f, y, radius1, radius1 + y, 180f, 90f)
        shapePath.lineTo(x - radius2, y)
        shapePath.addArc(x - radius2, y - radius2, x, y, angle, -90f)
        shapePath.lineTo(x, radius1)
        shapePath.addArc(x, 0f, radius1 + x, radius1, 180f, 90f)
    }
}