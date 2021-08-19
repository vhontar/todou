package com.easycodingstudio.todou.model

import android.os.Parcelable
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Long,
    val task: String,
    val isCompleted: Boolean,
    val isImportant: Boolean,
    val categoryId: Long
): Parcelable

fun Todo.toEntity() = TodoEntity(id, task, isCompleted, isImportant, categoryId = categoryId)