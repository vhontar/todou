package com.easycoding.todou.model

data class CategoryWithTodos(
    val category: Category,
    val todos: List<Todo>
)
