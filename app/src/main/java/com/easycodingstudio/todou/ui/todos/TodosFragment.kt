package com.easycodingstudio.todou.ui.todos

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
import com.easycodingstudio.todou.databinding.FragmentTodosBinding
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.ui.adapter.TodoAdapter
import com.easycodingstudio.todou.ui.todou.TodouFragmentDirections
import com.easycodingstudio.todou.ui.todou.TodouViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TodosFragment: Fragment(R.layout.fragment_todos) {
    private lateinit var viewDataBinding: FragmentTodosBinding
    private val viewModel: TodosViewModel by viewModels()

    private lateinit var adapter: TodoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTodosBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewModel.apply {
            categoryWithTodos.observe(viewLifecycleOwner) {
                adapter = TodoAdapter(it.category, viewModel)
                viewDataBinding.rvTodos.adapter = adapter

                adapter.submitList(it.todos)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.todosEvents.collect { event ->
                when(event) {
                    is TodouViewModel.TodouEvents.NavigateToTodoPage -> {
                        val action = TodouFragmentDirections.actionTodouFragmentToTodoFragment(event.todo.id)
                        findNavController().navigate(action)
                    }
                    is TodouViewModel.TodouEvents.NavigateToArchivePage -> {

                    }
                    else -> { }
                }
            }
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
}