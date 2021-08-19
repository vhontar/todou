package com.easycodingstudio.todou.util

import android.content.res.ColorStateList
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.widget.ImageView
import android.widget.TextView
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
        R.color.category_color_1 -> R.drawable.category_background_1
        R.color.category_color_2 -> R.drawable.category_background_2
        R.color.category_color_3 -> R.drawable.category_background_3
        R.color.category_color_4-> R.drawable.category_background_4
        R.color.category_color_5 -> R.drawable.category_background_5
        R.color.category_color_6 -> R.drawable.category_background_6
        R.color.category_color_7 -> R.drawable.category_background_7
        R.color.category_color_8 -> R.drawable.category_background_8
        R.color.category_color_9 -> R.drawable.category_background_9
        R.color.category_color_10 -> R.drawable.category_background_10
        R.color.category_color_11 -> R.drawable.category_background_11
        R.color.category_color_12 -> R.drawable.category_background_12
        R.color.category_color_13 -> R.drawable.category_background_13
        R.color.category_color_14 -> R.drawable.category_background_14
        R.color.category_color_15 -> R.drawable.category_background_15
        R.color.category_color_16 -> R.drawable.category_background_16
        R.color.category_color_17 -> R.drawable.category_background_17
        R.color.category_color_18 -> R.drawable.category_background_18
        else -> R.drawable.category_background_1
    })
}

@BindingAdapter("colorTint")
fun AppCompatImageView.setColorTint(colorInt: Int) {
    if (colorInt != 0) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorInt))
    }
}