package com.easycoding.todou.model

import com.easycoding.todou.data.entities.CategoryEntity

data class Category(
    val id: Int,
    val name: String,
    val color: Int,
    val order: Int
)

fun Category.toEntity() = CategoryEntity(id, name, color, order)