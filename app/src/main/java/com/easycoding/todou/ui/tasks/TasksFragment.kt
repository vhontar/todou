package com.easycoding.todou.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.easycoding.todou.R
import com.easycoding.todou.databinding.FragmentTasksBinding

class TasksFragment: Fragment(R.layout.fragment_tasks) {

    private lateinit var viewDataBinding: FragmentTasksBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTasksBinding.bind(view)
    }
}