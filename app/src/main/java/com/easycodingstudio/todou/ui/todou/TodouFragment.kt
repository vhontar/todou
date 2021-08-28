package com.easycodingstudio.todou.ui.todou

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentTodouBinding
import com.easycodingstudio.todou.helper.DrawerHelper
import com.easycodingstudio.todou.ui.adapter.CategoryMenuAdapter
import com.easycodingstudio.todou.ui.adapter.TodoWithHeaderAdapter
import com.easycodingstudio.todou.util.exclusive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TodouFragment: Fragment(R.layout.fragment_todou) {
    private lateinit var viewDataBinding: FragmentTodouBinding
    private val viewModel: TodouViewModel by viewModels()

    private lateinit var adapter: TodoWithHeaderAdapter

    private lateinit var drawerHelper: DrawerHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        viewDataBinding = FragmentTodouBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewDataBinding.apply {
            drawerHelper = DrawerHelper(requireActivity(), drawerLayout, homePage)

            ivMenu.setOnClickListener { drawerHelper.onMenuClicked() }

            includedDrawerMenu.rlHome.setOnClickListener {
                drawerHelper.close()
                viewModel.onHomeScreenItemClicked()
            }
            viewModel.categories.observe(viewLifecycleOwner) {
                val adapter = CategoryMenuAdapter(viewModel)
                includedDrawerMenu.rvCategories.adapter = adapter
                adapter.submitList(it)
            }
        }

        viewModel.apply {
            todosWithHeader.observe(viewLifecycleOwner) {
                adapter = TodoWithHeaderAdapter(viewModel)
                adapter.submitList(it)
                viewDataBinding.rvTodosWithHeader.adapter = adapter
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.todouEvents.collect { event ->
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
                    is TodouViewModel.TodouEvents.CloseDrawerNavigation -> {
                        drawerHelper.close()
                    }
                }.exclusive
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.todou_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            else -> super.onOptionsItemSelected(item)
        }
    }
}