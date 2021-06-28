package com.easycodingstudio.todou.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easycodingstudio.todou.model.Todo
import java.util.*

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "important") val isImportant: Boolean = false,
    @ColumnInfo(name = "date_created") val dateCreated: Long = Date().time,
    @ColumnInfo(name = "category_id") val categoryId: Int = 0
)

fun TodoEntity.toModel() = Todo(id, name, isCompleted, isImportant)
fun List<TodoEntity>.toModels() = map { it.toModel() }
