package com.easycoding.todou.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.easycoding.todou.model.CategoryWithTodos

data class CategoryWithTodosEntity(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val todosEntities: List<TodoEntity>
)

fun CategoryWithTodosEntity.toModel() = CategoryWithTodos(
    category = categoryEntity.toModel(),
    todos = todosEntities.toModels()
)

fun List<CategoryWithTodosEntity>.toModels() = map {
    CategoryWithTodos(
        category = it.categoryEntity.toModel(),
        todos = it.todosEntities.toModels()
    )
}