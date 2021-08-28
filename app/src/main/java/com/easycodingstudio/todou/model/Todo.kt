package com.easycodingstudio.todou.model

import android.os.Parcelable
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

@Parcelize
data class Todo(
    val id: Long = 0,
    var task: String = "",
    var isCompleted: Boolean = false,
    var importance: TodoImportance = TodoImportance.DEFAULT,
    var categoryId: Long = 1,
    var todoDate: DateTime? = null,
    var notificationId: Long? = null
): Parcelable {
    fun isTodoImportant() = importance == TodoImportance.HIGH_IMPORTANCE
}

enum class TodoImportance {
    DEFAULT,
    SMALL_IMPORTANCE,
    MEDIUM_IMPORTANCE,
    HIGH_IMPORTANCE
}

fun Todo.toEntity() = TodoEntity(task = task, isCompleted = isCompleted, importance = importance, todoDate = todoDate?.millis, categoryId = categoryId)