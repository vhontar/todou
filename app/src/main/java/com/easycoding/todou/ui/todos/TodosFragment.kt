package com.easycoding.todou.ui.todos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.easycoding.todou.R
import com.easycoding.todou.databinding.FragmentTodosBinding

class TodosFragment: Fragment(R.layout.fragment_todos) {

    private lateinit var viewDataBinding: FragmentTodosBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTodosBinding.bind(view)
    }
}