package com.mhy.text_stroke;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.mhy.shape_textview.R;

/** 使用画笔绘制描边
 * Created By Mahongyin
 * Date    2025/10/15 16:55
 * 文字描边View
 */
public class TextStrokeView extends AppCompatTextView {
    /**
     * 描边画笔
     */
    private final Paint mStrokePaint = new Paint();
    private int mTextStrokeColor;
    private float mTextStrokeSize = 2f;

    public TextStrokeView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public TextStrokeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TextStrokeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        //获取自定义的属性名称
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextStrokeView);
        //获取对应的属性值
        mTextStrokeColor = a.getColor(R.styleable.TextStrokeView_textStrokeColor, Color.BLACK);
        mTextStrokeSize = a.getDimension(R.styleable.TextStrokeView_textStrokeWidth, mTextStrokeSize);
        a.recycle();
    }

    public void setTextStrokeColor(@ColorInt int color) {
        //更新 轮廓颜色
        mTextStrokeColor = color;
        invalidate();
    }

    public void setTextStrokeWidth(int width) {
        //更新 轮廓宽度  默认4
        mTextStrokeSize = width;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mTextStrokeSize > 0f) {
            String text = getText().toString();
            mStrokePaint.set(getPaint());//复制原来TextView画笔中的一些参数
            // 这里要把 原本Shader 清掉，避免把文本渐变色也带进来
            mStrokePaint.setShader(null);
            // 设置抗锯齿
            mStrokePaint.setAntiAlias(true);
            // 设置防抖动
            mStrokePaint.setDither(true);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            // 描边宽度
            mStrokePaint.setStrokeWidth(mTextStrokeSize);
            mStrokePaint.setColor(mTextStrokeColor);
            //上面mStrokePaint.set()已包含
//            mStrokePaint.setTextSize(getPaint().getTextSize());
//            mStrokePaint.setFlags(getPaint().getFlags());
//            mStrokePaint.setAlpha(getPaint().getAlpha());
//            mStrokePaint.setTypeface(getPaint().getTypeface());
//            //设置粗体
//            mStrokePaint.setFakeBoldText(getPaint().isFakeBoldText());
            // 绘制文本描边
            canvas.drawText(text, (getWidth() - mStrokePaint.measureText(text)) / 2, getBaseline(), mStrokePaint);
        }
//        // 绘制原文本内容
//        if (mTextSolidColor != Color.TRANSPARENT) {
//            getPaint().setColor(getCurrentTextColor()); // 原文颜色
//            canvas.drawText(getText().toString(), (getWidth() - getPaint().measureText(getText().toString())) / 2, getBaseline(), getPaint());
//        }
        super.onDraw(canvas);
    }

}
