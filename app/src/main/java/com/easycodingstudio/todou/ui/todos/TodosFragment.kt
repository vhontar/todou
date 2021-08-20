package com.easycodingstudio.todou.ui.todos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentTodosBinding
import com.easycodingstudio.todou.ui.adapter.TodoAdapter
import dagger.hilt.android.AndroidEntryPoint

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

        viewDataBinding.apply {
            
        }

        viewModel.apply {
            categoryWithTodos.observe(viewLifecycleOwner) {
                adapter = TodoAdapter(it.category, viewModel)
                viewDataBinding.rvTodos.adapter = adapter

                adapter.submitList(it.todos)
            }
        }
    }
}