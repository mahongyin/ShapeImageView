package com.mhy.shapeimageview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils.attachBadgeDrawable
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.mhy.shape_imageview.ShapeDrawable
import com.mhy.shapeimageview.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                Snackbar.make(slider, "onSilde:${slider.value}", Snackbar.LENGTH_SHORT).show();

            }
        })
        mBinding.slider.setLabelFormatter { value -> "V:$value$" }

        mBinding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(@NonNull slider: RangeSlider) {

            }

            override fun onStopTrackingTouch(@NonNull slider: RangeSlider) {
                Snackbar.make(slider, "onSilde:${slider.values}", Snackbar.LENGTH_SHORT).show();
            }
        })
        ShapeDrawable.cardViewShape(mBinding.cardView)
        ShapeDrawable.imageShape(mBinding.myImag)
        ShapeDrawable.shadowShape(this,mBinding.flTv)
        initButton()
        initImageView()
        initTabLayout()
        initTextView()
    }

    private fun initImageView() {
        mBinding.sivBadge.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            @SuppressLint("UnsafeOptInUsageError")
            override fun onGlobalLayout() {
                BadgeDrawable.create(this@MainActivity).apply {
                    badgeGravity = BadgeDrawable.TOP_END
                    number = 99999
                    // badge最多显示字符，默认999+ 是4个字符（带'+'号）
                    maxCharacterCount = 3
                    backgroundColor =
                        ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_dark)
                    attachBadgeDrawable(this, mBinding.sivBadge, mBinding.flImg)
                }
                mBinding.sivBadge.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun initButton() {
        mBinding.btn1.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            @SuppressLint("UnsafeOptInUsageError")
            override fun onGlobalLayout() {
                BadgeDrawable.create(this@MainActivity).apply {
                    badgeGravity = BadgeDrawable.TOP_START
                    number = 6
                    backgroundColor =
                        ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_dark)
                    // MaterialButton本身有间距，不设置为0dp的话，可以设置badge的偏移量
                    verticalOffset = 15
                    horizontalOffset = 10
                    attachBadgeDrawable(this, mBinding.btn1, mBinding.flBtn)
                }
                mBinding.btn1.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun initTabLayout() {
        // 带数字小红点
        mBinding.tabLayout.getTabAt(0)?.let {
            it.orCreateBadge.apply {
                backgroundColor =
                    ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_dark)
                badgeTextColor = ContextCompat.getColor(this@MainActivity, R.color.white)
                number = 6
            }
        }

        // 不带数字小红点
        mBinding.tabLayout.getTabAt(1)?.let {
            it.orCreateBadge.apply {
                backgroundColor =
                    ContextCompat.getColor(this@MainActivity, android.R.color.holo_red_dark)
                badgeTextColor = ContextCompat.getColor(this@MainActivity, R.color.white)
            }
        }
    }

    private fun initTextView() {
        // 在视图树变化
        mBinding.tvBadge.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                BadgeDrawable.create(this@MainActivity).apply {
                    badgeGravity = BadgeDrawable.TOP_END
                    number = 6
                    backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.purple_200)
                    isVisible = true
                    attachBadgeDrawable(this, mBinding.tvBadge,mBinding.flTv)
                }
                mBinding.tvBadge.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
        mBinding.tvBadge.background=ShapeDrawable.getShape7()
    }
}