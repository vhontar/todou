package com.easycodingstudio.todou.ui.category

import androidx.lifecycle.*
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.constants.ArgumentConstants
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.Color
import com.easycodingstudio.todou.repository.TodouRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val todouRepository: TodouRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId = savedStateHandle.get<Long>(ArgumentConstants.ARG_CATEGORY_ID) ?: -1

    val category: MutableLiveData<Category> = if (categoryId != -1L) {
        todouRepository.getCategory(categoryId).asLiveData() as MutableLiveData<Category>
    } else {
        MutableLiveData(Category())
    }
    val categoryName = MediatorLiveData<String>()

    private val categoryEventsChannel = Channel<CategoryEvents>()
    val categoryEvents = categoryEventsChannel.receiveAsFlow()

    init {
        categoryName.addSource(category) {
            categoryName.value = it.name
        }
    }

    fun save() = viewModelScope.launch {
        if (categoryName.value.isNullOrEmpty()) {
            categoryEventsChannel.send(CategoryEvents.ShowErrorMessage(R.string.category_name_empty))
            return@launch
        }
        category.value?.let {
            it.name = categoryName.value ?: it.name
            if (categoryId == -1L) todouRepository.createCategory(it)
            else todouRepository.updateCategory(it)
            // close page
            categoryEventsChannel.send(CategoryEvents.CloseCategoryPage)
        }
    }

    fun onColorClicked(color: Color) {
        val localCategory = category.value
        localCategory?.color = color.id
        category.value = localCategory
    }

    sealed class CategoryEvents {
        class ShowErrorMessage(val stringId: Int): CategoryEvents()
        object CloseCategoryPage: CategoryEvents()
    }
}