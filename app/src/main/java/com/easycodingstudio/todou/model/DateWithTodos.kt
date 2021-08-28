package com.easycodingstudio.todou.model

import org.joda.time.DateTime

data class DateWithTodos(
    val date: DateTime,
    val todos: List<Todo>
)
