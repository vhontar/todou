package com.easycodingstudio.todou.model

import android.os.Parcelable
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.data.database.entities.CategoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long = 0,
    var name: String = "",
    var color: Int = R.color.category_color_1,
    var selected: Boolean = false,
    var cannotBeRemoved: Boolean = false,
    val order: Int = 0
): Parcelable

fun Category.toEntity() = CategoryEntity(id, name, color, selected, cannotBeRemoved, order)
fun List<Category>.toEntities() = map { it.toEntity() }