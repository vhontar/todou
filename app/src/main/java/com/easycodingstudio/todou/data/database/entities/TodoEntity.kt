package com.easycodingstudio.todou.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easycodingstudio.todou.model.Todo
import org.joda.time.DateTime

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "important") val isImportant: Boolean = false,
    @ColumnInfo(name = "date_created") val dateCreated: Long = DateTime.now().millis,
    @ColumnInfo(name = "category_id") val categoryId: Long
)

fun TodoEntity.toModel() = Todo(id, task, isCompleted, isImportant, categoryId)
fun List<TodoEntity>.toModels() = map { it.toModel() }
