package com.easycodingstudio.todou.model

data class TodoWithCategory(
    val category: Category,
    val todo: Todo
): Holder
