package com.mhy.shape_textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 文字附近图片设置指定大小
 * 添加点击事件
 * //ViewConfiguration 中定义的相关常量
 * //ViewConfiguration.getLongPressTimeout(); // 长按超时时间，默认约 500ms
 * //ViewConfiguration.getTapTimeout();     // 点击超时时间，默认约 100ms
 */
public class DrawableTextView extends AppCompatTextView {
    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;

    private static final int DIRECTION_WIDTH = 0;
    private static final int DIRECTION_HEIGHT = 1;
    private final float drawableWidth;
    private final float drawableHeight;
    private DrawableListener drawableRightListener;
    private DrawableListener drawableLeftListener;
    private DrawableListener drawableTopListener;
    private DrawableListener drawableBottomListener;
    private long clickDownTime = 0;

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
        setCompoundDrawablesWithIntrinsicBounds(drawables[DRAWABLE_LEFT], drawables[DRAWABLE_TOP],
                drawables[DRAWABLE_RIGHT], drawables[DRAWABLE_BOTTOM]);
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

    //设置drawableRight 图片的点击监听
    public void setDrawableRightListener(DrawableListener drawableRightListener) {
        if (!isClickable()) {
            setClickable(true);
        }
        this.drawableRightListener = drawableRightListener;
    }

    public void setDrawableLeftListener(DrawableListener drawableLeftListener) {
        if (!isClickable()) {
            setClickable(true);
        }
        this.drawableLeftListener = drawableLeftListener;
    }

    public void setDrawableTopListener(DrawableListener drawableTopListener) {
        if (!isClickable()) {
            setClickable(true);
        }
        this.drawableTopListener = drawableTopListener;
    }

    public void setDrawableBottomListener(DrawableListener drawableBottomListener) {
        if (!isClickable()) {
            setClickable(true);
        }
        this.drawableBottomListener = drawableBottomListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clickDownTime = SystemClock.elapsedRealtime();
                break;
            case MotionEvent.ACTION_UP:
                long clickUpTime = SystemClock.elapsedRealtime();
                if (clickUpTime - clickDownTime < ViewConfiguration.getLongPressTimeout()) {
                    if (drawableRightListener != null) {
                        Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                        if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())
                                && event.getRawX() < getRight()) {
                            drawableRightListener.onClick(drawableRight);
                            return true;
                        }
                    }
                    if (drawableLeftListener != null) {
                        Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                        if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())
                                && event.getRawX() > getLeft()) {
                            drawableLeftListener.onClick(drawableLeft);
                            return true;
                        }
                    }
                    if (drawableTopListener != null) {
                        Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
                        if (drawableTop != null && event.getRawY() <= (getTop() + drawableTop.getBounds().height())
                                && event.getRawY() > getTop()) {
                            drawableTopListener.onClick(drawableTop);
                            return true;
                        }
                    }
                    if (drawableBottomListener != null) {
                        Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
                        if (drawableBottom != null && event.getRawY() >= (getBottom() - drawableBottom.getBounds().height())
                                && event.getRawY() < getBottom()) {
                            drawableBottomListener.onClick(drawableBottom);
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public interface DrawableListener {
        void onClick(Drawable drawable);
    }
}

