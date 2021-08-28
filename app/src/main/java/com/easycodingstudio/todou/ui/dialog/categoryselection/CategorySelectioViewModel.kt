package com.easycodingstudio.todou.ui.dialog.categoryselection

import androidx.lifecycle.*
import com.easycodingstudio.todou.constants.ArgumentConstants
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategorySelectioViewModel @Inject constructor(
    todouRepository: TodouRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val filteredCategories = MediatorLiveData<List<Category>>()
    val searchRequest = MutableLiveData<String>()

    private val selectedCategoryId = savedStateHandle.get<Long>(ArgumentConstants.ARG_CATEGORY_ID)
    private val categories = todouRepository.getCategories().asLiveData()

    init {
        filteredCategories.addSource(categories) { c ->
            c.forEach { it.selected = it.id == selectedCategoryId  }
            filteredCategories.value = c
        }
        filteredCategories.addSource(searchRequest) { request ->
            val list = categories.value?.filter { it.name.contains(request, ignoreCase = true) } ?: listOf()
            filteredCategories.value = list
        }
    }

    fun isDarkModeEnabled() = true
}