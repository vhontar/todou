package com.easycoding.todou.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.easycoding.todou.data.database.dao.CategoryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryDao: CategoryDao,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
}