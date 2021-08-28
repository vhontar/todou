package com.easycodingstudio.todou.ui.todo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentTodoBinding
import com.easycodingstudio.todou.ui.dialog.categoryselection.CategorySelectionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class TodoFragment : Fragment(R.layout.fragment_todo) {
    private lateinit var viewDataBinding: FragmentTodoBinding
    private val viewModel: TodoViewModel by viewModels()

    private var categorySelectionDialog: CategorySelectionDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTodoBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.todoEvents.collect {
                when (it) {
                    is TodoViewModel.TodoEvents.OpenCategorySelectionDialog -> {
                        categorySelectionDialog =
                            CategorySelectionDialog.newInstance(
                                viewModel.category.value?.id ?: 1,
                                viewModel
                            )
                        categorySelectionDialog?.show(childFragmentManager, null)
                    }
                    is TodoViewModel.TodoEvents.OpenDateSelectionDialog -> {

                    }
                    is TodoViewModel.TodoEvents.OpenLocalNotificationSetupDialog -> {

                    }
                    is TodoViewModel.TodoEvents.CloseTodoPage -> {
                        findNavController().navigateUp()
                    }
                    is TodoViewModel.TodoEvents.CloseCategorySelectionDialog -> {
                        categorySelectionDialog?.dismiss()
                    }
                }
            }
        }
    }
}