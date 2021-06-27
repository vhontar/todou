package com.easycoding.todou.ui.todou

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.easycoding.todou.R
import com.easycoding.todou.databinding.FragmentTodouBinding

class TodouFragment: Fragment(R.layout.fragment_todou) {

    private lateinit var viewDataBinding: FragmentTodouBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentTodouBinding.bind(view)
    }
}