package com.easycodingstudio.todou.ui.todou

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentTodouBinding
import com.easycodingstudio.todou.model.SortOrder
import com.easycodingstudio.todou.ui.adapter.CategoryWithTodosAdapter
import com.easycodingstudio.todou.util.exclusive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class TodouFragment: Fragment(R.layout.fragment_todou) {
    private lateinit var viewDataBinding: FragmentTodouBinding
    private val viewModel: TodouViewModel by viewModels()

    private lateinit var adapter: CategoryWithTodosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = CategoryWithTodosAdapter(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        viewDataBinding = FragmentTodouBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewDataBinding.apply {
            val itemDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            rvCategoriesWithTodos.adapter = adapter
            rvCategoriesWithTodos.addItemDecoration(itemDecorator)

            tvArchive.setOnClickListener {
                viewModel.onArchiveClicked()
            }
        }

        viewModel.categoriesWithTodos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val event = viewModel.todouEvents.first()
            when(event) {
                is TodouViewModel.TodouEvents.NavigateToCategoryWithAllTodosPage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToTodosFragment(event.category.id)
                    findNavController().navigate(action)
                }
                is TodouViewModel.TodouEvents.NavigateToCategoryPage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToCategoryFragment(event.category.id)
                    findNavController().navigate(action)
                }
                is TodouViewModel.TodouEvents.NavigateToTodoPage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToTodoFragment(event.todo.id)
                    findNavController().navigate(action)
                }
                is TodouViewModel.TodouEvents.NavigateToArchivePage -> {
                    val action = TodouFragmentDirections.actionTodouFragmentToArchiveFragment()
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
}