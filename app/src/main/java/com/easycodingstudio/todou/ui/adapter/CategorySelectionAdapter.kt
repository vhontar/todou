package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewCategorySelectionItemBinding
import com.easycodingstudio.todou.model.Category

class CategorySelectionAdapter(
    private val listener: OnCategorySelectionListener?,
    private val darkMode: Boolean = false
) : ListAdapter<Category, CategorySelectionViewHolder>(CategorySelectionDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategorySelectionViewHolder.from(parent, listener, darkMode)
    override fun onBindViewHolder(holder: CategorySelectionViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class CategorySelectionViewHolder(
    private val binding: RecyclerviewCategorySelectionItemBinding,
    private val listener: OnCategorySelectionListener?,
    private val darkMode: Boolean = false
) : RecyclerView.ViewHolder(binding.root) {

    private var localCategory: Category? = null

    init {
        binding.llCategory.setOnClickListener { onCategorySelectionItemClicked() }
    }

    fun bind(category: Category) {
        localCategory = category

        binding.category = category
        binding.darkMode = darkMode
        binding.executePendingBindings()
    }

    private fun onCategorySelectionItemClicked() {
        localCategory?.let {
            listener?.onCategorySelectionItemClicked(it)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            listener: OnCategorySelectionListener?,
            darkMode: Boolean
        ): CategorySelectionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategorySelectionItemBinding.inflate(inflater, parent, false)
            return CategorySelectionViewHolder(binding, listener, darkMode)
        }
    }
}

class CategorySelectionDiffUtilCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
}

interface OnCategorySelectionListener {
    fun onCategorySelectionItemClicked(category: Category)
}