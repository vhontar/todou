package com.easycodingstudio.todou.model

import android.content.Context
import androidx.core.content.ContextCompat
import com.easycodingstudio.todou.R

data class TodoHeader(
    val type: TodoHeaderType = TodoHeaderType.TODAY,
    val allTodosCount: Int = 0,
    val finishedTodosCount: Int = 0
): Holder {
    fun getHeaderString(context: Context): String {
        return when (type) {
            TodoHeaderType.TODAY -> context.resources.getQuantityString(R.plurals.today, allTodosCount, allTodosCount)
            TodoHeaderType.TOMORROW -> context.resources.getQuantityString(R.plurals.tomorrow, allTodosCount, allTodosCount)
            else -> context.resources.getQuantityString(R.plurals.later, allTodosCount, allTodosCount)
        }
    }

    fun getHeaderColor(context: Context): Int {
        return when (type) {
            TodoHeaderType.TODAY -> ContextCompat.getColor(context, R.color.today)
            TodoHeaderType.TOMORROW -> ContextCompat.getColor(context, R.color.tomorrow)
            else -> ContextCompat.getColor(context, R.color.later)
        }
    }
}