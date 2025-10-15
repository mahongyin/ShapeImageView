package com.mhy.shape_textview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class TextGradientView extends AppCompatTextView {
    private final int startColor;
    private final int endColor;
    private final int centerColor;
    private final int orientation; // 0:horizontal, 1:  vertical

    public TextGradientView(Context context) {
        this(context, null);
    }

    public TextGradientView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextGradientView, defStyleAttr, 0);
        startColor = ta.getColor(R.styleable.TextGradientView_textGradient_startColor, 0);
        centerColor = ta.getColor(R.styleable.TextGradientView_textGradient_centerColor, 0);
        endColor = ta.getColor(R.styleable.TextGradientView_textGradient_endColor, 0);
        orientation = ta.getInt(R.styleable.TextGradientView_textGradient_orientation, 0);
        ta.recycle();
    }

    //性能优化
    //在 onDraw() 中每次都会创建 LinearGradient，对于频繁重绘的场景（如动画）可能会增加开销。
    // 可以将 LinearGradient 缓存到成员变量，并在 onSizeChanged() 时重新计算。
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {  // 确保尺寸有效
            updateShader(w, h);
        }
    }

    private void updateShader(int w, int h) {
        Shader shader;
        if (orientation == 1) {
            if (centerColor != 0) {
                shader = new LinearGradient(0, 0, 0, h, new int[]{startColor, centerColor, endColor}, null, Shader.TileMode.CLAMP);
            } else {
                shader = new LinearGradient(0, 0, 0, h, startColor, endColor, Shader.TileMode.CLAMP);
            }
        } else {
            if (centerColor != 0) {
                shader = new LinearGradient(0, 0, w, 0, new int[]{startColor, centerColor, endColor}, null, Shader.TileMode.CLAMP);
            } else {
                shader = new LinearGradient(0, 0, w, 0, startColor, endColor, Shader.TileMode.CLAMP);
            }
        }
        getPaint().setShader(shader);// add
        postInvalidate();
    }
}