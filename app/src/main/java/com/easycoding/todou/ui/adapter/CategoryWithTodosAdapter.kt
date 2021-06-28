package com.easycoding.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.todou.databinding.RecyclerviewCategoryWithTodosItemBinding
import com.easycoding.todou.databinding.RecyclerviewCategoryWithoutTodosItemBinding
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.CategoryWithTodos

class CategoryWithTodosAdapter(
    private val categoryListener: CategoryListener,
    private val todoListener: TodoListener
) : ListAdapter<CategoryWithTodos, RecyclerView.ViewHolder>(CategoryDiffUtilCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryWithTodosViewHolder -> getItem(position)?.let { holder.bind(it) }
            is CategoryWithoutTodosViewHolder -> getItem(position)?.let { holder.bind(it.category) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CategoryViewHolderTypes.CATEGORY_WITH_TODOS.value ->
                CategoryWithTodosViewHolder.from(parent, categoryListener, todoListener)
            CategoryViewHolderTypes.CATEGORY_WITHOUT_TODOS.value ->
                CategoryWithoutTodosViewHolder.from(parent, categoryListener)
            else -> throw ClassNotFoundException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return super.getItemViewType(position)

        if (item.todos.isEmpty())
            return CategoryViewHolderTypes.CATEGORY_WITHOUT_TODOS.value
        return CategoryViewHolderTypes.CATEGORY_WITH_TODOS.value
    }
}

class CategoryWithTodosViewHolder private constructor(
    private val binding: RecyclerviewCategoryWithTodosItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var categoryListener: CategoryListener? = null
    private var todoListener: TodoListener? = null

    init {
        binding.tvCategoryNameWithTodos.setOnClickListener { categoryListener?.categoryItemClicked() }
    }

    fun bind(categoryWithTodos: CategoryWithTodos) {
        binding.apply {
            category = categoryWithTodos.category

            val adapter = TodoAdapter(
                categoryWithTodos.category,
                todoListener
            )
            adapter.submitList(categoryWithTodos.todos.take(4))
            rvTodos.adapter = adapter

            executePendingBindings()
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            categoryListener: CategoryListener,
            todoListener: TodoListener
        ): CategoryWithTodosViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategoryWithTodosItemBinding.inflate(inflater, parent, false)
            val viewHolder = CategoryWithTodosViewHolder(binding)
            viewHolder.categoryListener = categoryListener
            viewHolder.todoListener = todoListener
            return viewHolder
        }
    }
}

class CategoryWithoutTodosViewHolder private constructor(
    private val binding: RecyclerviewCategoryWithoutTodosItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var categoryListener: CategoryListener? = null

    init {
        binding.tvCategoryTitle.setOnClickListener { categoryListener?.categoryItemClicked() }
    }

    fun bind(category: Category) {
        binding.category = category
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, categoryListener: CategoryListener): CategoryWithoutTodosViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategoryWithoutTodosItemBinding.inflate(layoutInflater, parent, false)
            val viewHolder = CategoryWithoutTodosViewHolder(binding)
            viewHolder.categoryListener = categoryListener
            return viewHolder
        }
    }
}

interface CategoryListener {
    fun categoryItemClicked()
}

class CategoryDiffUtilCallback : DiffUtil.ItemCallback<CategoryWithTodos>() {
    override fun areItemsTheSame(oldItem: CategoryWithTodos, newItem: CategoryWithTodos) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CategoryWithTodos, newItem: CategoryWithTodos) =
        oldItem.category.id == newItem.category.id
}

enum class CategoryViewHolderTypes(val value: Int) {
    CATEGORY_WITH_TODOS(1),
    CATEGORY_WITHOUT_TODOS(2)
}