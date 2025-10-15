package com.mhy.shape_textview.gradient_span;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.mhy.shape_textview.R;

/**
 * Created By Mahongyin
 * Date    2022/8/13 20:14
 * 文字渐变色
 * 还有一种 sharder LinGradient
 */

public class TextGradientView extends AppCompatTextView {
    private int mStartColor = 0;
    private int mEndColor = 0;
    private int mCenterColor = 0;
    private int mOrientation = 0;

    public TextGradientView(@NonNull Context context) {
        this(context, null);
    }

    public TextGradientView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextGradientView(@NonNull Context context, @Nullable AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextGradientView);
        if (typedArray == null) {
            return;
        }
        mStartColor = typedArray.getColor(R.styleable.TextGradientView_textGradient_startColor, 0);
        mEndColor = typedArray.getColor(R.styleable.TextGradientView_textGradient_endColor, 0);
        mCenterColor = typedArray.getColor(R.styleable.TextGradientView_textGradient_centerColor, 0);
        mOrientation = typedArray.getInt(R.styleable.TextGradientView_textGradient_orientation, 0);
        typedArray.recycle();
        if (!TextUtils.isEmpty(getText())) {
            setText(getText());
        }
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        SpannableString spannableString = new SpannableString(text);
        int[] colors;
        //float[] positions;
        if (mCenterColor != 0) {
            colors = new int[]{mStartColor, mCenterColor, mEndColor};
            //positions = new float[]{0, 0.5f, 1};
        } else {
            colors = new int[]{mStartColor, mEndColor};
            //positions = new float[]{0, 1};
        }
        // positions = null 默认就是均匀0-1分布
        GradientSpan gradientFontSpan = new GradientSpan(colors, null/*positions*/, mOrientation);
        spannableString.setSpan(gradientFontSpan, 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        super.setText(spannableString, type);
        invalidate();
    }
}