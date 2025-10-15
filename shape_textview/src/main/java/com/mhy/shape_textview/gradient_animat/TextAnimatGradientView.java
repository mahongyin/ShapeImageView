package com.mhy.shape_textview.gradient_animat;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.widget.AppCompatTextView;

import com.mhy.shape_textview.R;

// 说明：实现从左到右文字渐变高亮动画
// attrs.xml
/*
<resources>
    <declare-styleable name="GradientTextView">
        <attr name="startColor" format="color" />
        <attr name="endColor"   format="color" />
        <attr name="duration"   format="integer" />
        <attr name="repeatCount" format="integer" />
        <attr name="alphaSync"  format="boolean" />
        <!-- 方向：0=左到右，1=右到左 -->
        <attr name="direction"  format="enum">
            <enum name="left_to_right" value="0"/>
            <enum name="right_to_left" value="1"/>
        </attr>
    </declare-styleable>
</resources>
*/

public class TextAnimatGradientView extends AppCompatTextView {
    private int startColor = 0xFF000000;   // 默认黑
    private int centerColor = 0;
    private int endColor = 0xFFFFFF00;   // 默认黄
    private long duration = 1500;         // ms
    private int repeatCount = ValueAnimator.INFINITE;
    private boolean alphaSync = false;
    private int direction = 0;             // 0: L→R, 1: R→L
    private LinearGradient linearGradient;
    private Matrix shaderMatrix = new Matrix();
    private float translateX = 0f;
    private ValueAnimator animator;
    private boolean isFirst = true;

    public TextAnimatGradientView(Context c) {
        super(c);
        init(null);
    }

    public TextAnimatGradientView(Context c, AttributeSet attrs) {
        super(c, attrs);
        init(attrs);
    }

    public TextAnimatGradientView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);
        init(attrs);
    }

    /**
     * 读取自定义属性
     */
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TextAnimatGradientView);
            startColor = ta.getColor(R.styleable.TextAnimatGradientView_animatorGradient_startColor, startColor);
            centerColor = ta.getColor(R.styleable.TextAnimatGradientView_animatorGradient_centerColor, centerColor);
            endColor = ta.getColor(R.styleable.TextAnimatGradientView_animatorGradient_endColor, endColor);
            duration = ta.getInteger(R.styleable.TextAnimatGradientView_animatorGradient_duration, (int) duration);
            repeatCount = ta.getInteger(R.styleable.TextAnimatGradientView_animatorGradient_repeatCount, repeatCount);
            alphaSync = ta.getBoolean(R.styleable.TextAnimatGradientView_animatorGradient_alphaSync, alphaSync);
            direction = ta.getInt(R.styleable.TextAnimatGradientView_animatorGradient_direction, direction);
            ta.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isFirst) {
            return;
        }
        startGradient();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopGradient();
    }

    /**
     * 在尺寸变化时创建 Shader
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0) {
            int[] colors;
            if (centerColor != 0) {
                colors = new int[]{startColor, centerColor, endColor};//123顺
            } else {
                colors = new int[]{startColor, endColor, startColor}; //101交替
            }
            // 从 -w 到 +w 生成渐变
            linearGradient = new LinearGradient(
                    direction == 0 ? -w : w, 0,
                    0, 0,
                    colors,
                    null,
                    Shader.TileMode.CLAMP);
            getPaint().setShader(linearGradient);

            if (isFirst) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        startGradient();
                        isFirst = false;
                    }
                });
            }
        }
    }

    /**
     * 启动渐变动画
     */
    public void startGradient() {
        if (animator != null && animator.isRunning()) return;
        // 动画从 0 → width*2 或 width*-2
        float start = 0f;
        float end = direction == 0 ? getWidth() * 2f : -getWidth() * 2f;
        animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(duration);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            translateX = (float) animation.getAnimatedValue();
            shaderMatrix.setTranslate(translateX, 0);
            if (linearGradient != null) {
                linearGradient.setLocalMatrix(shaderMatrix);
            }
            if (alphaSync) {
                // alpha 同步：周期性从 0→1→0
                float frac = animation.getAnimatedFraction();
                setAlpha(1f - Math.abs(frac * 2f - 1f));
            }
            invalidate();
        });
        animator.start();
    }

    /**
     * 停止动画
     */
    public void stopGradient() {
        if (animator != null) {
            animator.cancel();
            animator = null;
            setAlpha(1f);
        }
    }

    // 动态修改属性示例
    public void setStartColor(int c) {
        this.startColor = c;
        rebuildShader();
    }

    public void setEndColor(int c) {
        this.endColor = c;
        rebuildShader();
    }

    public void setDuration(long d) {
        this.duration = d;
    }
    // … 其余 setter 略 …

    /**
     * 重建 Shader（在动态修改颜色后调用）
     */
    private void rebuildShader() {
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

}

// XML 布局示例：res/layout/activity_main.xml
/*
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:padding="16dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.example.gradienttext.GradientTextView
        android:id="@+id/tv_gradient"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Android 从左往右淡入淡出渐变示例"
        android:textSize="24sp"
        android:textColor="#FF000000"
        app:startColor="#FF000000"
        app:endColor="#FFFF0000"
        app:duration="2000"
        app:repeatCount="infinite"
        app:alphaSync="true"
        app:direction="left_to_right"/>
</LinearLayout>
*/

// 使用示例：MainActivity.java
/*
public class MainActivity extends Activity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        GradientTextView gtv = findViewById(R.id.tv_gradient);
        // 延迟启动以待布局完成
        gtv.post(gtv::startGradient);
    }
}
*/
