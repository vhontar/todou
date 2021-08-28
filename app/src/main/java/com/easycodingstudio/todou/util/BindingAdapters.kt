package com.easycodingstudio.todou.util

import android.content.res.ColorStateList
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.easycodingstudio.todou.R

@BindingAdapter("strike")
fun AppCompatTextView.setStrike(show: Boolean) {
    paintFlags = if (show) {
        paintFlags or STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter("categoryBackground")
fun AppCompatTextView.categoryBackground(color: Int) {
    background = ContextCompat.getDrawable(context, when(color) {
        R.color.category_color_1 -> R.drawable.category_background_1_selected
        R.color.category_color_2 -> R.drawable.category_background_2_selected
        R.color.category_color_3 -> R.drawable.category_background_3_selected
        R.color.category_color_4-> R.drawable.category_background_4_selected
        R.color.category_color_5 -> R.drawable.category_background_5_selected
        R.color.category_color_6 -> R.drawable.category_background_6_selected
        R.color.category_color_7 -> R.drawable.category_background_7_selected
        R.color.category_color_8 -> R.drawable.category_background_8_selected
        R.color.category_color_9 -> R.drawable.category_background_9_selected
        R.color.category_color_10 -> R.drawable.category_background_10_selected
        R.color.category_color_11 -> R.drawable.category_background_11_selected
        R.color.category_color_12 -> R.drawable.category_background_12_selected
        R.color.category_color_13 -> R.drawable.category_background_13_selected
        R.color.category_color_14 -> R.drawable.category_background_14_selected
        R.color.category_color_15 -> R.drawable.category_background_15_selected
        R.color.category_color_16 -> R.drawable.category_background_16_selected
        R.color.category_color_17 -> R.drawable.category_background_17_selected
        R.color.category_color_18 -> R.drawable.category_background_18_selected
        else -> R.drawable.category_background_1_selected
    })
}

@BindingAdapter("colorTint")
fun AppCompatImageView.setColorTint(colorInt: Int) {
    if (colorInt != 0) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorInt))
    }
}