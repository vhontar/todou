package com.easycoding.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.todou.databinding.RecyclerviewCategoryItemBinding
import com.easycoding.todou.model.CategoryWithTodos

class CategoryWithTodosAdapter : PagingDataAdapter<CategoryWithTodos, CategoryViewHolder>(CategoryDiffUtilCallback()) {
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)
    }
}

class CategoryViewHolder(
    private val binding: RecyclerviewCategoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {

    }

    fun bind(categoryWithTodos: CategoryWithTodos) {
        binding.apply {
            category = categoryWithTodos.category

            rvTodos.adapter

            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup): CategoryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategoryItemBinding.inflate(inflater, parent, false)
            return CategoryViewHolder(binding)
        }
    }
}

class CategoryDiffUtilCallback : DiffUtil.ItemCallback<CategoryWithTodos>() {
    override fun areItemsTheSame(oldItem: CategoryWithTodos, newItem: CategoryWithTodos): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryWithTodos, newItem: CategoryWithTodos): Boolean {
        return oldItem.category.id == newItem.category.id
    }


}