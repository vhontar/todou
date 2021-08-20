package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewCategoryWithTodosItemBinding
import com.easycodingstudio.todou.databinding.RecyclerviewCategoryWithoutTodosItemBinding
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.CategoryWithTodos

class CategoryWithTodosAdapter(
    private val listener: OnCategoryTodoItemListener
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
                CategoryWithTodosViewHolder.from(parent, listener)
            CategoryViewHolderTypes.CATEGORY_WITHOUT_TODOS.value ->
                CategoryWithoutTodosViewHolder.from(parent, listener)
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
    private val binding: RecyclerviewCategoryWithTodosItemBinding,
    private val listener: OnCategoryTodoItemListener
) : RecyclerView.ViewHolder(binding.root) {

    private var localCategory: Category? = null

    init {
        /**
         * It's cheaper to subscribe to listeners only for created view holders and reuse them when new
         * data binding to the created view holder
         * */
        binding.tvCategoryNameWithTodos.setOnClickListener { onCategoryItemClicked() }
        binding.ivMoreTodos.setOnClickListener { onCategoryWithAllTodosClicked() }
    }

    fun bind(categoryWithTodos: CategoryWithTodos) {
        // set up local variable for listeners
        localCategory = categoryWithTodos.category

        binding.apply {
            category = categoryWithTodos.category
            todosMoreThan4 = categoryWithTodos.todos.size > 4

            val adapter = TodoAdapter(
                categoryWithTodos.category,
                listener
            )
            adapter.submitList(categoryWithTodos.todos.take(4))
            rvTodos.adapter = adapter

            executePendingBindings()
        }
    }

    private fun onCategoryItemClicked() {
        localCategory?.let { listener.onCategoryItemClicked(it) }
    }

    private fun onCategoryWithAllTodosClicked() {
        localCategory?.let { listener.onCategoryWithAllTodosClicked(it) }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            listener: OnCategoryTodoItemListener
        ): CategoryWithTodosViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategoryWithTodosItemBinding.inflate(inflater, parent, false)
            return CategoryWithTodosViewHolder(binding, listener)
        }
    }
}

class CategoryWithoutTodosViewHolder private constructor(
    private val binding: RecyclerviewCategoryWithoutTodosItemBinding,
    private val listener: OnCategoryTodoItemListener
) : RecyclerView.ViewHolder(binding.root) {

    private var localCategory: Category? = null

    init {
        /**
         * It's cheaper to subscribe to listeners only for created view holders and reuse them when new
         * data binding to the created view holder
         * */
        binding.tvCategoryTitle.setOnClickListener { onCategoryItemClicked() }
        binding.root.setOnClickListener { onCategoryWithAllTodosClicked() }
    }

    fun bind(category: Category) {
        // set local variable for listeners
        localCategory = category

        binding.category = category
        binding.executePendingBindings()
    }

    private fun onCategoryItemClicked() {
        localCategory?.let { listener.onCategoryItemClicked(it) }
    }

    private fun onCategoryWithAllTodosClicked() {
        localCategory?.let { listener.onCategoryWithAllTodosClicked(it) }
    }

    companion object {
        fun from(parent: ViewGroup, listener: OnCategoryTodoItemListener): CategoryWithoutTodosViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewCategoryWithoutTodosItemBinding.inflate(layoutInflater, parent, false)
            return CategoryWithoutTodosViewHolder(binding, listener)
        }
    }
}

class CategoryDiffUtilCallback : DiffUtil.ItemCallback<CategoryWithTodos>() {
    override fun areItemsTheSame(oldItem: CategoryWithTodos, newItem: CategoryWithTodos) =
        oldItem == newItem
    override fun areContentsTheSame(oldItem: CategoryWithTodos, newItem: CategoryWithTodos) =
        oldItem.category.id == newItem.category.id
}

interface OnCategoryTodoItemListener: OnTodoItemListener {
    fun onCategoryItemClicked(category: Category)
    fun onCategoryWithAllTodosClicked(category: Category)
}

enum class CategoryViewHolderTypes(val value: Int) {
    CATEGORY_WITH_TODOS(1),
    CATEGORY_WITHOUT_TODOS(2)
}