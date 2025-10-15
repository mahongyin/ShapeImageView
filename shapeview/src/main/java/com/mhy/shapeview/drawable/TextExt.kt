package com.mhy.shapeview.drawable

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.mhy.shapeview.drawable.Utils


/**
 * Created By Mahongyin
 * Date    2022/11/6 14:28
 *
 */
fun TextView.leftDrawable(size: Int, @DrawableRes drawableRes: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableRes)
    drawable?.setBounds(0, 0, Utils.dp2px(context, size), Utils.dp2px(context, size))
    setCompoundDrawables(drawable, null, null, null)
}

fun TextView.topDrawable(size: Int, @DrawableRes drawableRes: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableRes)
    drawable?.setBounds(0, 0, Utils.dp2px(context, size), Utils.dp2px(context, size))
    setCompoundDrawables(null, drawable, null, null)
}

fun TextView.rightDrawable(size: Int, @DrawableRes drawableRes: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableRes)
    drawable?.setBounds(0, 0, Utils.dp2px(context, size), Utils.dp2px(context, size))
    setCompoundDrawables(null, null, drawable, null)
}

fun TextView.bottomDrawable(size: Int, @DrawableRes drawableRes: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableRes)
    drawable?.setBounds(0, 0, Utils.dp2px(context, size), Utils.dp2px(context, size))
    setCompoundDrawables(null, null, null, drawable)
}