package com.mhy.shape_textview.gradient_span;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created By Mahongyin
 * Date    2022/8/13 20:07
 *
 * @author mahongyin
 * 渐变色
 */

public class GradientSpan extends ReplacementSpan {
    private int mSize;
    private final int[] mColors;
    private float[] mPositions = null;
    /**
     * 0 左-右，180 右-左， 90 上-下，270/-90 下-上，45 左上-右下，315/-45 左下-右上，135 右上-左下， 225 右下-左上
     */
//    private int angle = 0;
    // 0横, 1竖
    private int mOrientation = 0;

    private Point mPoint1 = new Point(0, 0);
    private Point mPoint2 = new Point(0, 0);

    public GradientSpan(int[] colors, float[] positions) {
        mColors = colors;
        mPositions = positions;
    }

    public GradientSpan(int[] colors) {
        mColors = colors;
    }

    public GradientSpan(int[] colors, int mOrientation) {
        mColors = colors;
        this.mOrientation = mOrientation;
    }

    public GradientSpan(int[] colors, float[] positions, int mOrientation) {
        mColors = colors;
        mPositions = positions;
        this.mOrientation = mOrientation;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end,
                       @Nullable Paint.FontMetricsInt fm) {
        mSize = Math.round(paint.measureText(text, start, end));
        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        if (fm != null) {
            fm.top = metrics.top;
            fm.ascent = metrics.ascent;
            fm.descent = metrics.descent;
            fm.bottom = metrics.bottom;
        }
        if (mOrientation == 1) {
            mPoint1 = new Point(0, 0);
            mPoint2 = new Point(0, metrics.bottom);
        } else {
            mPoint1 = new Point(0, 0);
            mPoint2 = new Point(mSize, 0);
        }
        return mSize;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        LinearGradient gradient = new LinearGradient(mPoint1.x, mPoint1.y, mPoint2.x, mPoint2.y,
                mColors, mPositions, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawText(text, start, end, x, y, paint);
    }
}