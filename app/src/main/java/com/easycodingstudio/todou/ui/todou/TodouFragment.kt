package com.easycodingstudio.todou.ui.todou

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentTodouBinding
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.ui.adapter.OnCategoryClickListener
import com.easycodingstudio.todou.ui.adapter.CategoryWithTodosAdapter
import com.easycodingstudio.todou.ui.adapter.OnTodoClickListener
import com.easycodingstudio.todou.util.exclusive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todou_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter_by_name -> {
                viewModel.onSortOrderSelected(SortOrder.SORT_BY_NAME)
                true
            }
            R.id.action_filter_by_date -> {
                viewModel.onSortOrderSelected(SortOrder.SORT_BY_DATE)
                true
            }
            R.id.action_hide_completed -> {
                item.isChecked = !item.isChecked
                viewModel.onHideCompletedClicked(item.isChecked)
                true
            }
            R.id.action_delete_all_completed -> {
                viewModel.onDeleteAllCompletedClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
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