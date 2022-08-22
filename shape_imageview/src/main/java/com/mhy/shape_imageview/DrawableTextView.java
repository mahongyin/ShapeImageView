package com.mhy.shape_imageview;

/**
 * Created By Mahongyin
 * Date    2022/8/22 18:48
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 可自定义Drawable的TextView
 * strokeWidth 可设置字体粗细 中粗建议0.3f 默认不加粗
 */
public class DrawableTextView extends androidx.appcompat.widget.AppCompatTextView {

    private final int DRAWABLE_LEFT = 0;
    private final int DRAWABLE_TOP = 1;
    private final int DRAWABLE_RIGHT = 2;
    private final int DRAWABLE_BOTTOM = 3;
    private final int leftDrawableWidth;
    private final int leftDrawableHeight;
    private final int rightDrawableWidth;
    private final int rightDrawableHeight;
    private final int topDrawableWidth;
    private final int topDrawableHeight;
    private final int bottomDrawableWidth;
    private final int bottomDrawableHeight;
    private final float strokeWidth;
    private int leftWidth, rightWidth;//左右图片宽度
    private DrawableListener.DrawableRightListener drawableRightListener;
    private DrawableListener.DrawableLeftListener drawableLeftListener;
    private DrawableListener.DrawableTopListener drawableTopListener;
    private DrawableListener.DrawableBottomListener drawableBottomListener;
    private Context context;

    public DrawableTextView(Context context) {
        this(context, null);
        init(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);

    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView,
                defStyleAttr, 0);
        leftDrawableHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_leftDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        leftDrawableWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_leftDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        rightDrawableHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_rightDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        rightDrawableWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_rightDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        topDrawableHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_topDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        topDrawableWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_topDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        bottomDrawableHeight = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_bottomDrawableHeight,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        bottomDrawableWidth = typedArray.getDimensionPixelSize(
                R.styleable.DrawableTextView_bottomDrawableWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -1,
                        getResources().getDisplayMetrics()));
        strokeWidth = typedArray.getFloat(R.styleable.DrawableTextView_StrokeWidth, 0);
        typedArray.recycle();
        init(context);


    }

    private void init(Context context) {
        //自定义加粗程度 建议0.3f
        if (strokeWidth != 0) {
            TextPaint paint = getPaint();
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        drawable();
    }

    private void drawable() {
        Drawable[] drawables = getCompoundDrawables();
        for (int i = 0; i < drawables.length; i++) {
            setDrawableSize(drawables[i], i);
        }
        //放置图片
        setCompoundDrawables(drawables[DRAWABLE_LEFT], drawables[DRAWABLE_TOP],
                drawables[DRAWABLE_RIGHT], drawables[DRAWABLE_BOTTOM]);
    }

    //设置drawableRight 图片的点击监听
    public void setDrawableRightListener(DrawableListener.DrawableRightListener drawableRightListener) {
        this.drawableRightListener = drawableRightListener;
    }

    public void setDrawableLeftListener(DrawableListener.DrawableLeftListener drawableLeftListener) {
        this.drawableLeftListener = drawableLeftListener;
    }

    public void setDrawableTopListener(DrawableListener.DrawableTopListener drawableTopListener) {
        this.drawableTopListener = drawableTopListener;
    }

    public void setDrawableBottomListener(DrawableListener.DrawableBottomListener drawableBottomListener) {
        this.drawableBottomListener = drawableBottomListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (drawableRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())
                            && event.getRawX() < getRight()) {
                        drawableRightListener.drawableRightListener(this);
                        return true;
                    }
                }

                if (drawableLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())
                            && event.getRawX() > getLeft()) {
                        drawableLeftListener.drawableLeftListener(this);
                        return true;
                    }
                }
                if (drawableTopListener != null) {
                    Drawable drawableTop = getCompoundDrawables()[DRAWABLE_TOP];
                    if (drawableTop != null && event.getRawY() <= (getTop() + drawableTop.getBounds().height())
                            && event.getRawY() > getTop()) {
                        drawableTopListener.drawableTopListener(this);
                        return true;
                    }
                }

                if (drawableBottomListener != null) {
                    Drawable drawableBottom = getCompoundDrawables()[DRAWABLE_BOTTOM];
                    if (drawableBottom != null && event.getRawY() >= (getBottom() - drawableBottom.getBounds().height())
                            && event.getRawY() < getBottom()) {
                        drawableBottomListener.drawableBottomListener(this);
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //设置图片的高度和宽度
    private void setDrawableSize(Drawable drawable, int index) {
        if (drawable == null) {
            return;
        }
        //左上右下
        int width = 0, height = 0;

        switch (index) {

            case DRAWABLE_LEFT:

                width = leftDrawableWidth;
                height = leftDrawableHeight;

                break;

            case DRAWABLE_TOP:

                width = topDrawableWidth;
                height = topDrawableWidth;
                break;

            case DRAWABLE_RIGHT:

                width = rightDrawableWidth;
                height = rightDrawableWidth;

                break;

            case DRAWABLE_BOTTOM:
                width = bottomDrawableWidth;
                height = bottomDrawableHeight;
                break;
        }

        //如果没有设置图片的高度和宽度具使用默认的图片高度和宽度
        if (width < 0) {

            width = drawable.getIntrinsicWidth();

        }

        if (height < 0) {

            height = drawable.getIntrinsicHeight();
        }

        if (index == 0) {

            leftWidth = width;

        } else if (index == 2) {

            rightWidth = width;
        }

        drawable.setBounds(0, 0, width, height);

    }
    public static class DrawableListener {


        public interface DrawableRightListener{

            void drawableRightListener(View view);

        }

        public interface DrawableLeftListener{

            void drawableLeftListener(View view);

        }


        public interface DrawableTopListener{

            void drawableTopListener(View view);

        }


        public interface DrawableBottomListener{

            void drawableBottomListener(View view);

        }



    }

}