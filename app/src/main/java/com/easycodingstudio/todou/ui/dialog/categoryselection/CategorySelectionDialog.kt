package com.easycodingstudio.todou.ui.dialog.categoryselection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.constants.ArgumentConstants
import com.easycodingstudio.todou.databinding.AlertdialogCategorySelectionBinding
import com.easycodingstudio.todou.ui.adapter.CategorySelectionAdapter
import com.easycodingstudio.todou.ui.adapter.OnCategorySelectionListener

class CategorySelectionDialog : DialogFragment(R.layout.alertdialog_category_selection) {
    private lateinit var viewDataBinding: AlertdialogCategorySelectionBinding
    private val viewModel: CategorySelectioViewModel by viewModels()

    private var listener: OnCategorySelectionListener? = null

    private lateinit var adapter: CategorySelectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = AlertdialogCategorySelectionBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel
        viewDataBinding.apply {
            viewModel.filteredCategories.observe(viewLifecycleOwner) {
                adapter = CategorySelectionAdapter(listener, viewModel.isDarkModeEnabled())
                rvCategories.adapter = adapter
                adapter.submitList(it)
            }
        }
    }

    companion object {
        fun newInstance(categoryId: Long, listener: OnCategorySelectionListener): CategorySelectionDialog {
            val args = Bundle().apply {
                putLong(ArgumentConstants.ARG_CATEGORY_ID, categoryId)
            }

            val dialog = CategorySelectionDialog()
            dialog.listener = listener
            dialog.arguments = args
            return dialog
        }
    }
}