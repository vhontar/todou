package com.easycodingstudio.todou.ui.category

import androidx.lifecycle.*
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.constants.ArgumentConstants
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.Color
import com.easycodingstudio.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val todouRepository: TodouRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId = savedStateHandle.get<Long>(ArgumentConstants.ARG_CATEGORY_ID) ?: -1

    val category: LiveData<Category> = if (categoryId != -1L) {
        todouRepository.getCategory(categoryId).asLiveData()
    } else {
        MutableLiveData(Category())
    }

    fun save() = viewModelScope.launch {
        if (categoryId == -1L) {
            category.value?.let {
                todouRepository.createCategory(it)
            }
        }
    }

    fun onColorClicked(color: Color) {
        // TODO save color
    }
}