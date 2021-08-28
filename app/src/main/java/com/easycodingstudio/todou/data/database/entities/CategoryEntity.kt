package com.easycodingstudio.todou.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easycodingstudio.todou.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: Int,
    @ColumnInfo(name = "selected") val selected: Boolean = false,
    @ColumnInfo(name = "cannot_be_removed") val cannotBeRemoved: Boolean = false,
    @ColumnInfo(name = "order") val order: Int
)

fun CategoryEntity.toModel() = Category(id, name, color, selected, cannotBeRemoved, order)
fun List<CategoryEntity>.toModels() = map { it.toModel() }
