package com.google.android.material.edge

import android.graphics.PointF
import androidx.annotation.Size
import com.google.android.material.shape.ShapePath
import com.google.android.material.shape.TriangleEdgeTreatment
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

/**
 * 边上三角形
 * @param sizePx 三角形底(也就是宿主的边重叠部分)到三角顶点的长度即高。sizePx(高)*2=三角形底（宿主的边重叠部分）的长度
 * @param inside 顶角朝内
 */
class MyTriangleEdgeTreatment(@Size(min = 0) val sizePx: Float, val inside: Boolean) :
    TriangleEdgeTreatment(sizePx, inside) {
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {//顶角太尖了，加个圆角
        // 绘制圆角线条
//            path.lineTo(width.toFloat(), (height / 2).toFloat())
//            val rectf = RectF((width - height).toFloat(), 0f, width.toFloat(), height.toFloat())
//            path.arcTo(rectf, 90f, 180f)
        val radius = sizePx / 4
        val offset = 3f //第一个点和第三个点中间靠靠 以减小顶点角度
        if (inside) {//内
            shapePath.lineTo(center - (sizePx * interpolation) + offset, 0f)//边起点到边上第一顶点角
            val p1 = PointF(center - (sizePx * interpolation) + offset, 0f)
            val p2 = PointF(center, sizePx * interpolation)//需要圆角点
            val p3 = PointF(center + (sizePx * interpolation) - offset, 0f)
            lineToAndCorner(shapePath, radius, radius, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
            shapePath.lineTo(center + (sizePx * interpolation) - offset, 0f)//圆弧结束处到边上第二顶点角
            //shapePath.lineTo(center, sizePx * interpolation, center + (sizePx * interpolation), 0f)//顶点到边上第二顶点角
            shapePath.lineTo(length, 0f)//到边末尾
        } else {
            //shapePath.lineTo(center - (sizePx * interpolation), 0f, center, -sizePx * interpolation)
            /*shapePath.lineTo(0f, 0f+(sizePx * interpolation))//往里裁剪*/
            shapePath.lineTo(
                center - (sizePx * interpolation) + offset,
                0f + (sizePx * interpolation)
            )//到第一点
            val p1 =
                PointF(center - (sizePx * interpolation) + offset, 0f + (sizePx * interpolation))
            val p2 = PointF(center, /*-sizePx * interpolation*/0f)//需要圆角点
            val p3 =
                PointF(center + (sizePx * interpolation) - offset, 0f + (sizePx * interpolation))
            lineToAndCorner(shapePath, radius, radius, p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
            shapePath.lineTo(
                center + (sizePx * interpolation) - offset,
                0f + (sizePx * interpolation), length, 0f/*+(sizePx * interpolation)//往里裁剪*/
            )
        }
    }

    /**
     * 画线并添加圆点 （绘制的线是起始点到圆角结束点的路径，并不包含到p3点路径,最后可用close自行闭合或lineTo）
     * @param path
     * @param startRadius 起始圆角半径中间点
     * @param endRadius   结束圆角半径中间点
     * @param x1          起始点
     * @param y1
     * @param x2          中间点
     * @param y2
     * @param x3          结束点
     * @param y3
     */
    fun lineToAndCorner(
        path: ShapePath,
        startRadius: Float,
        endRadius: Float,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        x3: Float,
        y3: Float
    ) {
        val onePoint: FloatArray = getOnLinePointLocationEnd(startRadius, x1, y1, x2, y2)
        path.lineTo(onePoint[0], onePoint[1])
        val twoPoint: FloatArray = getOnLinePointLocationStart(endRadius, x2, y2, x3, y3)
        //绘制圆角
        path.cubicToPoint(onePoint[0], onePoint[1], x2, y2, twoPoint[0], twoPoint[1])
    }

    /**
     * 获取线上点坐标
     *
     * @param lenght 线上点距离起始点(x1,y1）长度
     * @param x1     起始点x坐标
     * @param y1     起始点y坐标
     * @param x2     结束点x坐标
     * @param y2     结束点y坐标
     * @return
     */
    fun getOnLinePointLocationStart(
        lenght: Float,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float
    ): FloatArray {
        val degree = getDegree(x1, y1, x2, y2)
        val dx = getRightSideFromDegree(degree, lenght.toDouble())
        val dy = getLeftSideFromDegree(degree, lenght.toDouble())
        val v2 = x1 + dx
        val v3 = y1 + dy
        return floatArrayOf(v2.toFloat(), v3.toFloat())
    }

    /**
     * 获取线上点坐标
     *
     * @param lenght 线上点距离结束点（x2,y2）长度
     * @param x1     起始点x坐标
     * @param y1     起始点y坐标
     * @param x2     结束点x坐标
     * @param y2     结束点y坐标
     * @return
     */
    fun getOnLinePointLocationEnd(
        lenght: Float,
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float
    ): FloatArray {
        val degree: Double = getDegree(x1, y1, x2, y2)
        val dx: Double = getRightSideFromDegree(degree, lenght.toDouble())
        val dy: Double = getLeftSideFromDegree(degree, lenght.toDouble())
        val v2 = x2 - dx
        val v3 = y2 - dy
        return floatArrayOf(v2.toFloat(), v3.toFloat())
    }

    //两点间的角度
    private fun getDegree(sx: Float, sy: Float, tx: Float, ty: Float): Double {
        val nX = tx - sx
        val nY = ty - sy
        var angrad: Double
        var angel: Double
        var tpi: Double
        var tan: Float
        if (nX.compareTo(0.0f) != 0) {
            tan = abs((nY / nX).toDouble()).toFloat()
            angel = atan(tan.toDouble())
            angrad = if (nX.compareTo(0.0f) == 1) {
                if (nY.compareTo(0.0f) == 1 || nY.compareTo(0.0f) == 0
                ) {
                    angel
                } else {
                    2 * Math.PI - angel
                }
            } else {
                if (nY.compareTo(0.0f) == 1 || nY.compareTo(0.0f) == 0
                ) {
                    Math.PI - angel
                } else {
                    Math.PI + angel
                }
            }
        } else {
            tpi = Math.PI / 2
            angrad = if (nY.compareTo(0.0f) == 1) {
                tpi
            } else {
                -1 * tpi
            }
        }
        return Math.toDegrees(angrad)
    }

    /**
     * 直角三角形 根据角度和斜边求直角边
     *
     * @param degree 角度
     * @param width  斜边
     * @return 直角边长
     */
    private fun getRightSideFromDegree(degree: Double, width: Double): Double {
        val cos = cos(Math.toRadians(degree))
        return width * cos
    }

    private fun getLeftSideFromDegree(degree: Double, width: Double): Double {
        val sin = sin(Math.toRadians(degree))
        return width * sin
    }
}