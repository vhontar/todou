package com.easycodingstudio.todou.model

data class CategoryWithTodos(
    val category: Category,
    val todos: List<Todo>
)
