package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewCategoryMenuItemBinding
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.ui.todou.TodouViewModel

class CategoryMenuAdapter(
    private val viewModel: TodouViewModel
): ListAdapter<Category, CategoryMenuViewHolder>(CategoryDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryMenuViewHolder.from(parent, viewModel)
    override fun onBindViewHolder(holder: CategoryMenuViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CategoryMenuViewHolder private constructor(
    private val binding: RecyclerviewCategoryMenuItemBinding,
    private val viewModel: TodouViewModel
): RecyclerView.ViewHolder(binding.root) {

    private var localCategory: Category? = null

    init {
        binding.llCategory.setOnClickListener { onCategoryItemClicked() }
    }

    fun bind(category: Category) {
        localCategory = category

        binding.category = category
        binding.darkMode = viewModel.isDarkModeEnabled()

        binding.executePendingBindings()
    }

    private fun onCategoryItemClicked() {
        localCategory?.let {
            viewModel.onCategoryMenuItemClicked(it)
        }
    }

    companion object {
        fun from(parent: ViewGroup, viewModel: TodouViewModel): CategoryMenuViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategoryMenuItemBinding.inflate(inflater, parent, false)
            return CategoryMenuViewHolder(binding, viewModel)
        }
    }
}

class CategoryDiffUtilCallback: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
}

interface OnCategoryMenuItemListener {
    fun onHomeScreenItemClicked()
    fun onCategoryMenuItemClicked(category: Category)
}