package com.easycodingstudio.todou.model

import android.os.Parcelable
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Int,
    val task: String,
    val isCompleted: Boolean,
    val isImportant: Boolean,
    val categoryId: Int = 0
): Parcelable

fun Todo.toEntity() = TodoEntity(id, task, isCompleted, isImportant, categoryId = categoryId)