package com.mhy.shape_textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 只适用单行文本
 */
public class DrawableTextView extends AppCompatTextView {
    private static final int DIRECTION_WIDTH = 0;
    private static final int DIRECTION_HEIGHT = 1;
    private float drawableWidth;
    private float drawableHeight;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView, defStyleAttr, 0);
        drawableWidth = array.getDimension(R.styleable.DrawableTextView_text_drawable_width, 0);
        drawableHeight = array.getDimension(R.styleable.DrawableTextView_text_drawable_height, 0);
        array.recycle();

        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, getSize(left, DIRECTION_WIDTH),
                    getSize(left, DIRECTION_HEIGHT));
        }
        if (right != null) {
            right.setBounds(0, 0, getSize(right, DIRECTION_WIDTH),
                    getSize(right, DIRECTION_HEIGHT));
        }
        if (top != null) {
            top.setBounds(0, 0, getSize(top, DIRECTION_WIDTH),
                    getSize(top, DIRECTION_HEIGHT));
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, getSize(bottom, DIRECTION_WIDTH),
                    getSize(bottom, DIRECTION_HEIGHT));
        }
        setCompoundDrawables(left, top, right, bottom);
    }
    //获取图片的宽高
    private int getSize(Drawable drawable, int direction) {
        if (drawableWidth > 0 && drawableHeight > 0) {
            if (direction == DIRECTION_WIDTH) {
                return (int) drawableWidth;
            } else {
                return (int) drawableHeight;
            }
        } else {
            if (direction == DIRECTION_WIDTH) {
                return drawable.getIntrinsicWidth();
            } else {
                return drawable.getIntrinsicHeight();
            }
        }
    }
}

