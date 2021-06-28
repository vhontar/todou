package com.easycodingstudio.todou.model

import android.os.Parcelable
import com.easycodingstudio.todou.data.database.entities.TodoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Int,
    val name: String,
    val isCompleted: Boolean,
    val isImportant: Boolean,
    val categoryId: Int = 0
): Parcelable

fun Todo.toEntity() = TodoEntity(id, name, isCompleted, isImportant, categoryId = categoryId)