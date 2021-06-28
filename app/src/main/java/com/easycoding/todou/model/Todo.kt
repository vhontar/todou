package com.easycoding.todou.model

import android.os.Parcelable
import com.easycoding.todou.data.database.entities.TodoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Todo(
    val id: Int,
    val name: String,
    val isDone: Boolean,
    val isImportant: Boolean,
    val categoryId: Int = 0
): Parcelable

fun Todo.toEntity() = TodoEntity(id, name, isDone, isImportant, categoryId = categoryId)