package com.mhy.shapeview.devshape;

import android.view.View;

/**
 * Created By Mahongyin
 * Date    2022/11/5 23:19
 */
public interface IDevUtils<T, V extends View> {

    /**
     * 直接设置样式到view
     *
     * @param v 需要设置样式的view
     */
    void into(V v);



    /**
     * 返回Drawable样式
     *
     * @return T
     */
    T build();
}
