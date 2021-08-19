package com.easycodingstudio.todou.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.constants.ColorConstants
import com.easycodingstudio.todou.databinding.FragmentCategoryBinding
import com.easycodingstudio.todou.ui.adapter.ColorAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment: Fragment(R.layout.fragment_category) {
    private lateinit var viewDataBinding: FragmentCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var adapter: ColorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ColorAdapter(viewModel)
        adapter.submitList(ColorConstants.colorList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentCategoryBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewDataBinding.apply {
            rvColors.adapter = adapter
            rvColors.layoutManager = GridLayoutManager(requireContext(), 6)
            val itemDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
            rvColors.addItemDecoration(itemDecorator)

            btnSave.setOnClickListener {
                viewModel.save()
            }
        }
    }
}