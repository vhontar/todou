package com.easycoding.todou.model

import com.easycoding.todou.data.database.entities.TodoEntity

data class Todo(
    val id: Int,
    val todo: String,
    val isDone: Boolean,
    val isImportant: Boolean,
    val categoryId: Int = 0
)

fun Todo.toEntity() = TodoEntity(id, todo, isDone, isImportant, categoryId = categoryId)