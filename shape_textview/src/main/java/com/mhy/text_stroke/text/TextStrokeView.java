package com.mhy.text_stroke.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;

import com.mhy.shape_textview.R;

/**
 * 使用双层TextView实现描边
 * Created By Mahongyin
 * Date    2021/10/14 19:04
 * 描边文字View
 */

public class TextStrokeView extends AppCompatTextView {
    /**
     * 用于描边的TextView
     */
    private final TextView borderText;
    private int mStrokeColor;
    private float mStrokeWidth = 2f;

    public TextStrokeView(Context context) {
        super(context);
        borderText = new TextView(context);
        init(context, null);
    }

    public TextStrokeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        borderText = new TextView(context, attrs);
        init(context, attrs);
    }

    public TextStrokeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        borderText = new TextView(context, attrs, defStyle);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        //获取自定义的属性名称
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextStrokeView);
        //获取对应的属性值
        mStrokeColor = a.getColor(R.styleable.TextStrokeView_textStrokeColor, Color.BLACK);
        mStrokeWidth = a.getDimension(R.styleable.TextStrokeView_textStrokeWidth, mStrokeWidth);
        a.recycle();

        borderText.getPaint().set(getPaint());//拷贝Paint
        TextPaint textPaint = borderText.getPaint();
        textPaint.setShader(null);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.STROKE);
        //设置描边宽度
        textPaint.setStrokeWidth(mStrokeWidth);
        //设置描边颜色
        textPaint.setColor(mStrokeColor);
        textPaint.setTextSize(getPaint().getTextSize());
    }

    public void setTextStrokeColor(@ColorInt int color) {
        this.mStrokeColor = color;
        //更新 轮廓颜色
        borderText.postInvalidate();
    }

    public void setTextStrokeWidth(int width) {
        this.mStrokeWidth = width;
        //更新 轮廓颜色
        borderText.postInvalidate();
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        borderText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence mText = borderText.getText();
        //两个TextView上的文字必须一致
        if (mText == null || !mText.equals(this.getText())) {
            borderText.setText(getText());
            this.postInvalidate();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        borderText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        borderText.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        borderText.setGravity(getGravity());
        //同样字体
        if (getTypeface() != borderText.getTypeface()) {
            borderText.setTypeface(getTypeface());
        }
        if (getTextSize() != borderText.getTextSize()) {
            borderText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
        }
        if (borderText.getCurrentTextColor() != mStrokeColor) {
            borderText.setTextColor(mStrokeColor);
        }
        if (borderText.getPaint().getStrokeWidth() != mStrokeWidth) {
            borderText.getPaint().setStrokeWidth(mStrokeWidth);
        }

        borderText.draw(canvas);
        super.onDraw(canvas);
    }
}
