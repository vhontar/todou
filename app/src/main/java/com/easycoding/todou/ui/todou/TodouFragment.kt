package com.easycoding.todou.ui.todou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.easycoding.todou.R
import com.easycoding.todou.databinding.FragmentTodouBinding
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.Todo
import com.easycoding.todou.ui.adapter.OnCategoryClickListener
import com.easycoding.todou.ui.adapter.CategoryWithTodosAdapter
import com.easycoding.todou.ui.adapter.OnTodoClickListener
import com.easycoding.todou.util.exclusive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion

@AndroidEntryPoint
class TodouFragment: Fragment(R.layout.fragment_todou), OnCategoryClickListener, OnTodoClickListener {

    private lateinit var viewDataBinding: FragmentTodouBinding
    private val viewModel: TodouViewModel by viewModels()

    private val adapter = CategoryWithTodosAdapter(this, this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTodouBinding.bind(view)

        viewDataBinding.apply {
            rvCategoriesWithTodos.adapter = adapter
        }

        viewModel.categoriesWithTodos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            when(viewModel.todouEvents.first()) {
                is TodouViewModel.TodouEvents.NavigateToCategoryPage -> {

                }
                is TodouViewModel.TodouEvents.NavigateToTodoPage -> {

                }
            }.exclusive
        }
    }

    override fun onCategoryItemClicked(category: Category) {
        viewModel.onCategoryItemClicked(category)
    }
    override fun onTodoItemClicked(todo: Todo) {
        viewModel.onTodoItemClicked(todo)
    }
    override fun onTodoItemDoneClicked(todo: Todo) {
        viewModel.onTodoItemDoneClicked(todo)
    }
}