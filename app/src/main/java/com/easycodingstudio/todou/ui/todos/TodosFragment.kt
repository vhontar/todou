package com.easycodingstudio.todou.ui.todos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentTodosBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodosFragment: Fragment(R.layout.fragment_todos) {

    private lateinit var viewDataBinding: FragmentTodosBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTodosBinding.bind(view)
    }
}