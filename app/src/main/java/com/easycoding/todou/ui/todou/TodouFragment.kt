package com.easycoding.todou.ui.todou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
            val event = viewModel.todouEvents.first()
            when(event) {
                is TodouViewModel.TodouEvents.NavigateToCategoryWithAllTodosPage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToTodosFragment(event.category)
                    findNavController().navigate(action)
                }
                is TodouViewModel.TodouEvents.NavigateToCategoryPage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToCategoryFragment(event.category)
                    findNavController().navigate(action)
                }
                is TodouViewModel.TodouEvents.NavigateToTodoPage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToTodoFragment(event.todo)
                    findNavController().navigate(action)
                }
            }.exclusive
        }
    }

    override fun onCategoryWithAllTodosClicked(category: Category) {
        viewModel.onCategoryWithAllTodosClicked(category)
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