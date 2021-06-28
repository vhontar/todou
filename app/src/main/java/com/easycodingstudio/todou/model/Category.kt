package com.easycodingstudio.todou.model

import android.os.Parcelable
import com.easycodingstudio.todou.data.database.entities.CategoryEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val color: Int,
    val order: Int
): Parcelable

fun Category.toEntity() = CategoryEntity(id, name, color, order)