package com.easycoding.todou.util

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.easycoding.todou.R

@BindingAdapter("strike")
fun TextView.setStrike(show: Boolean) {
    paintFlags = if (show) {
        paintFlags or STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter("categoryBackground")
fun TextView.categoryBackground(color: Int) {
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
        else -> R.drawable.category_background_1
    })
}