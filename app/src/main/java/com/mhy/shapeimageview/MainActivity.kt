package com.mhy.shapeimageview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slider = findViewById<Slider>(R.id.slider)
        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                Snackbar.make(slider, "onSilde:${slider.value}", Snackbar.LENGTH_SHORT).show();

            }
        })
        slider.setLabelFormatter { value -> "V:$value$" }

        findViewById<RangeSlider>(R.id.range_slider).addOnSliderTouchListener(object :
            RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(@NonNull slider: RangeSlider) {

            }

            override fun onStopTrackingTouch(@NonNull slider: RangeSlider) {
                Snackbar.make(slider, "onSilde:${slider.values}",
                    Snackbar.LENGTH_SHORT).show();
            }
        })

    }

}