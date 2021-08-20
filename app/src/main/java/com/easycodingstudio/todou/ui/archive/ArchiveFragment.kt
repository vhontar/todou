package com.easycodingstudio.todou.ui.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentArchiveBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArchiveFragment: Fragment(R.layout.fragment_archive) {
    private lateinit var viewDataBinding: FragmentArchiveBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentArchiveBinding.bind(view)
    }
}