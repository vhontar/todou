package com.easycodingstudio.todou.ui.category

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.constants.ColorConstants
import com.easycodingstudio.todou.databinding.FragmentCategoryBinding
import com.easycodingstudio.todou.ui.adapter.ColorAdapter
import com.easycodingstudio.todou.ui.custom.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
            val itemDecorator = GridSpacingItemDecoration(
                6,
                resources.getDimension(R.dimen.vertical_divider).toInt(),
                false
            )
            rvColors.adapter = adapter
            rvColors.layoutManager = GridLayoutManager(requireContext(), 6)
            rvColors.addItemDecoration(itemDecorator)

            btnSave.setOnClickListener { viewModel.save() }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.categoryEvents.collect { event ->
                when(event) {
                    is CategoryViewModel.CategoryEvents.ShowErrorMessage -> {
                        Toast.makeText(requireContext(), event.stringId, Toast.LENGTH_LONG).show()
                    }
                    is CategoryViewModel.CategoryEvents.CloseCategoryPage -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }
}