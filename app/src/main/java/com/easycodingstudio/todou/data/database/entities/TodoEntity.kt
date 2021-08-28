package com.easycodingstudio.todou.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.model.TodoImportance
import org.joda.time.DateTime

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "completed") val isCompleted: Boolean = false,
    @ColumnInfo(name = "importance") val importance: TodoImportance = TodoImportance.DEFAULT,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "todo_date") val todoDate: Long? = null,
    @ColumnInfo(name = "notification_id") val notificationId: Long? = null
)

fun TodoEntity.toModel() = Todo(id, task, isCompleted, importance, categoryId, DateTime(todoDate), notificationId)
fun List<TodoEntity>.toModels() = map { it.toModel() }
