package com.easycoding.todou.model

import com.easycoding.todou.data.entities.TodoEntity

data class Todo(
    val id: Int,
    val todo: String,
    val isDone: Boolean,
    val isImportant: Boolean
)

fun Todo.toEntity() = TodoEntity(id, todo, isDone, isImportant)