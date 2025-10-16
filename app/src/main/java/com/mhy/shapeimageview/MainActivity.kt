package com.mhy.shapeimageview

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils.attachBadgeDrawable
import com.google.android.material.shape.MarkerEdgeTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.OffsetEdgeTreatment
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.mhy.shapeimageview.databinding.ActivityMainBinding
import com.mhy.shapeview.material.ShapeDrawable

//https://blog.csdn.net/chuyouyinghe/article/details/126042315
class MainActivity : AppCompatActivity() {

    private val mBinding by lazy {
        //ActivityMainBinding.bind(findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.drawableText.setDrawableLeftListener {
            Toast.makeText(this, "左侧", Toast.LENGTH_SHORT).show()
        }
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
//        mBinding.tvGradient.post { mBinding.tvGradient.startGradient() }

        val sam =  ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(10f.dp)
            .setBottomEdge(OffsetEdgeTreatment(MarkerEdgeTreatment(40f.dp), (0f).dp))
            .setTopEdge(TriangleEdgeTreatment(20f.dp, false))
            .build()
        mBinding.shape.background = MaterialShapeDrawable(sam).apply {
            setTint(Color.GRAY)
            setCornerSize(20f.dp)
            strokeWidth = 2f.dp
            strokeColor = ColorStateList.valueOf(Color.RED)
        }
    }
    private val Float.dp: Float get() = resources.displayMetrics.density * this
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
        mBinding.tvBadge.background= ShapeDrawable.getShape7(this)
    }
}