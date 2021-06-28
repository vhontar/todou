package com.easycoding.todou.ui.todou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.easycoding.todou.R
import com.easycoding.todou.databinding.FragmentTodouBinding
import com.easycoding.todou.model.Todo
import com.easycoding.todou.ui.adapter.CategoryListener
import com.easycoding.todou.ui.adapter.CategoryWithTodosAdapter
import com.easycoding.todou.ui.adapter.TodoListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodouFragment: Fragment(R.layout.fragment_todou), CategoryListener, TodoListener {

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
    }

    override fun onCategoryItemClicked() = viewModel.onCategoryItemClicked()
    override fun onTodoItemClicked() = viewModel.onTodoItemClicked()
    override fun onTodoItemDoneClicked(todo: Todo) = viewModel.onTodoItemDoneClicked(todo)
}