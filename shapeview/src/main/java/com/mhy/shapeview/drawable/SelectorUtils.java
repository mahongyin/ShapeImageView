package com.mhy.shapeview.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import static android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

/**
 * Created By Mahongyin
 * Date    2022/11/5 23:29
 */
public class SelectorUtils {
    //ShapeDrawable gf=new ShapeDrawable();
//    LayerDrawable gf=new LayerDrawable();
//    RippleDrawable gf=new RippleDrawable();
    public void setShape(GradientDrawable drawable, int shape) {

        switch (shape) {
            case GradientDrawable.RECTANGLE:
                drawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case GradientDrawable.OVAL:
                drawable.setShape(GradientDrawable.OVAL);
                break;
            case GradientDrawable.LINE:
                drawable.setShape(GradientDrawable.LINE);
                break;
            case GradientDrawable.RING:
                drawable.setShape(GradientDrawable.RING);
                break;
        }
    }

    private static void setGradientType(GradientDrawable drawable, int gradientType) {
        switch (gradientType) {
            case GradientDrawable.LINEAR_GRADIENT:
                drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
                break;
            case GradientDrawable.RADIAL_GRADIENT:
                drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                break;
            case GradientDrawable.SWEEP_GRADIENT:
                drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
                break;
        }
    }

    private static GradientDrawable.Orientation getAngle(int angle) {
        switch (angle) {
            case 0:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case 45:
                return GradientDrawable.Orientation.BL_TR;
            case 90:
                return GradientDrawable.Orientation.BOTTOM_TOP;
            case 135:
                return GradientDrawable.Orientation.BR_TL;
            case 180:
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case 225:
                return GradientDrawable.Orientation.TR_BL;
            case 270:
                return GradientDrawable.Orientation.TOP_BOTTOM;
            case 315:
                return GradientDrawable.Orientation.TL_BR;
        }
        return GradientDrawable.Orientation.TOP_BOTTOM;
    }
    private void initTv2RippleBG(View view) {

        int[][] stateList = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_activated},
                new int[]{}
        };

        //深蓝
        int normalColor = Color.parseColor("#303F9F");
        //玫瑰红
        int pressedColor = Color.parseColor("#FF4081");
        int[] stateColorList = new int[]{
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor
        };
        ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, null);
        view.setBackground(rippleDrawable);
    }
    /**
     * 作者：CnPeng
     * 时间：2018/8/8 下午12:02
     * 功用：以代码的方式构建rippleDrawable为背景——有drawable,设置mask
     * 说明：http://www.tothenew.com/blog/ripple-effect-in-android/
     * https://www.programcreek.com/java-api-examples/index.php?api=android.graphics.drawable.RippleDrawable
     * 设置水波纹的颜色
     *  android:background="@drawable/ripple"
     * ripple <?xml version="1.0" encoding="utf-8"?>
     * <ripple xmlns:android="http://schemas.android.com/apk/res/android"
     *     android:color="#ffffff"
     *     >
     *     <item android:drawable="@drawable/btn_ripple"/>
     * </ripple>
     *
     * btn_ripple<?xml version="1.0" encoding="utf-8"?>
     * <shape xmlns:android="http://schemas.android.com/apk/res/android"
     *     android:shape="rectangle"
     *     >
     *     <solid android:color="#11EA09"/>
     *     <corners android:radius="25dp"/>
     * </shape>
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initTv5RippleBG(View view) {

        int[][] stateList = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_activated},
                new int[]{}
        };

        //深蓝
        int normalColor = Color.parseColor("#303F9F");
        //玫瑰红
        int pressedColor = Color.parseColor("#FF4081");
        int[] stateColorList = new int[]{
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor
        };
        ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

        float[] outRadius = new float[]{10, 10, 15, 15, 20, 20, 25, 25};
        RoundRectShape roundRectShape = new RoundRectShape(outRadius, null, null);
        ShapeDrawable maskDrawable = new ShapeDrawable();
        maskDrawable.setShape(roundRectShape);
        maskDrawable.getPaint().setColor(Color.parseColor("#000000"));
        maskDrawable.getPaint().setStyle(Paint.Style.FILL);

        ShapeDrawable contentDrawable = new ShapeDrawable();
        contentDrawable.setShape(roundRectShape);
        contentDrawable.getPaint().setColor(Color.parseColor("#f7c653"));
        contentDrawable.getPaint().setStyle(Paint.Style.FILL);

        //contentDrawable实际是默认初始化时展示的；maskDrawable 控制了rippleDrawable的范围
        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, contentDrawable, maskDrawable);
        view.setBackground(rippleDrawable);
    }
    /**
     * 设置底部tab图标
     * @paramradioButton控件
     * @paramdrawableNormal常态时的图片
     * @paramdrawableSelect选中时的图片
     */

    public void setSelectorDrawable(View cbButton,Drawable drawableNormal, Drawable drawableSelect){
        StateListDrawable drawable =new StateListDrawable();
        //选中
        drawable.addState(new int[]{android.R.attr.state_selected},drawableSelect);
        //未选中
        drawable.addState(new int[]{-android.R.attr.state_selected},drawableNormal);
        drawable.addState(new int[]{},drawableNormal);
        cbButton.setBackground(drawable);
    }

    /**设置底部tab文字颜色
     * @paramradioButton控件
     * @paramnormal正常时的颜色值
     * @paramchecked选中时的颜色值
    注意：-android.R.attr.state_checked 和 android.R.attr.state_checked
    的区别在于 “-” 号代表值里的true 和 false ,有“-”为false 没有则为true
     */

    public void setSelectorColor(TextView radioButton, @ColorInt int normal, @ColorInt int checked){
        int[] colors =new int[] { normal,checked,normal};
        int[][] states =new int[3][];
        states[0] =new int[] { -android.R.attr.state_checked};
        states[1] =new int[] { android.R.attr.state_checked};
        states[2] =new int[] {};
        ColorStateList colorStateList =new ColorStateList(states,colors);
        radioButton.setTextColor(colorStateList);

    }

    /**
     * 获取Selector
     *
     * @param normalDraw
     * @param pressedDraw
     * @return
     */
    public static StateListDrawable getSelector(Drawable normalDraw, Drawable pressedDraw) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDraw);
        stateListDrawable.addState(new int[]{}, normalDraw);
        return stateListDrawable;
    }

    public float dp2px(Context context, float dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    /**
     * 设置shape(设置单独圆角)
     *
     * @param topLeftCA
     * @param topRigthCA
     * @param buttomLeftCA
     * @param buttomRightCA
     * @param bgColor
     * @param storkeWidth
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawable(Context mContext,float topLeftCA, float topRigthCA, float buttomLeftCA,
                                        float buttomRightCA, int bgColor, int storkeWidth, int strokeColor) {
        //把边框值设置成dp对应的px
        storkeWidth = (int) dp2px(mContext, storkeWidth);

        float[] circleAngleArr = {topLeftCA, topLeftCA, topRigthCA, topRigthCA,
                buttomLeftCA, buttomLeftCA, buttomRightCA, buttomRightCA};
        //把圆角设置成dp对应的px
        for (int i = 0; i < circleAngleArr.length; i++) {
            circleAngleArr[i] = dp2px(mContext, circleAngleArr[i]);
        }

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(circleAngleArr);//圆角
        gradientDrawable.setColor(bgColor); //背景色
        gradientDrawable.setStroke(storkeWidth, strokeColor); //边框宽度，边框颜色

        return gradientDrawable;
    }

    /**
     * 设置shape(圆角)
     *
     * @param bgCircleAngle
     * @param bgColor
     * @param width
     * @param strokeColor
     * @return
     */
    public GradientDrawable getDrawable(int bgCircleAngle, int bgColor, int width, int strokeColor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(bgCircleAngle);
        gradientDrawable.setColor(bgColor);
        gradientDrawable.setStroke(width, strokeColor);
        return gradientDrawable;
    }


    /**
     * 设置 shape 的颜色
     *
     * @param view
     * @param solidColor
     */
    public static void setShapeColor(View view, int solidColor) {
        if (view == null) {
            return;
        }
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(solidColor);
    }

    /**
     * 设置shape倒角和颜色
     *
     * @param view
     * @param solidColor
     * @param corner
     */
    public static void setShapeCorner2Color(View view, int solidColor, float corner) {
        if (view == null) {
            return;
        }
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(corner);
    }

    /**
     * 设置shape倒角 颜色 和描边颜色和大小
     *
     * @param view
     * @param solidColor
     * @param corner
     */
    public static void setShapeCorner2Color2Stroke(View view, int solidColor, float corner, int strokeColor, int strokeWidth) {
        if (view == null) {
            return;
        }
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(corner);
        gradientDrawable.setStroke(strokeWidth, strokeColor);
    }

    /**
     * 设置矩形渐变，同时shape渐变类型只能是线性从上倒下，颜色数组中的顺序即是渐变顺序
     *
     * @param view
     * @param colors
     */
    public static void setShapeGradient(View view, int[] colors) {
        if (view == null) {
            return;
        }
        if (colors.length > 3) {
            return;
        }
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        gradientDrawable.setGradientType(LINEAR_GRADIENT);
        gradientDrawable.setColors(colors);
    }
}
