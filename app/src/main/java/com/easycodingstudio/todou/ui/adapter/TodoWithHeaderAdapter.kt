package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewTodoHeaderItemBinding
import com.easycodingstudio.todou.databinding.RecyclerviewTodoItemBinding
import com.easycodingstudio.todou.model.*

private const val TODO_HEADER_ITEM = 1
private const val TODO_ITEM = 2

class TodoWithHeaderAdapter(
    private val listener: OnTodoItemListener
) : ListAdapter<Holder, RecyclerView.ViewHolder>(TodoDiffUtilCallBack()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            if (holder is TodoViewHolder) {
                it as TodoWithCategory
                holder.bind(it)
            } else if (holder is TodoHeaderViewHolder) {
                it as TodoHeader
                holder.bind(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TODO_HEADER_ITEM -> TodoHeaderViewHolder.from(parent, listener)
            TODO_ITEM -> TodoViewHolder.from(parent, listener)
            else -> throw ClassNotFoundException()
        }
    }

    override fun getItemViewType(position: Int) = if (getItem(position) is TodoHeader) TODO_HEADER_ITEM else TODO_ITEM
}

class TodoViewHolder private constructor(
    private val binding: RecyclerviewTodoItemBinding,
    private val listener: OnTodoItemListener
) : RecyclerView.ViewHolder(binding.root) {

    private var localTodo: Todo? = null

    init {
        /**
         * It's cheaper to subscribe to listeners only for created view holders and reuse them when new
         * data binding to the created view holder
         * */
        binding.tvTodoName.setOnClickListener { onTodoItemClicked() }
        binding.ivTodoDone.setOnClickListener { onTodoItemDoneClicked() }
    }

    fun bind(todoWithCategory: TodoWithCategory) {
        // set for listener
        localTodo = todoWithCategory.todo

        binding.todo = todoWithCategory.todo
        binding.category = todoWithCategory.category
        binding.executePendingBindings()
    }

    private fun onTodoItemClicked() {
        localTodo?.let {
            listener.onTodoItemClicked(it)
        }
    }

    private fun onTodoItemDoneClicked() {
        localTodo?.let {
            val updatedTodo = it.copy(isCompleted = !it.isCompleted)
            listener.onTodoItemDoneClicked(updatedTodo)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: OnTodoItemListener): TodoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewTodoItemBinding.inflate(layoutInflater, parent, false)
            return TodoViewHolder(binding, listener)
        }
    }
}

class TodoHeaderViewHolder(
    private val binding: RecyclerviewTodoHeaderItemBinding,
    private val listener: OnTodoItemListener
): RecyclerView.ViewHolder(binding.root) {

    private var todoHeader: TodoHeader? = null

    init {
        binding.tvTodosHeader.setOnClickListener { onCategoryItemClicked() }
    }

    fun bind(todoHeader: TodoHeader) {
        this.todoHeader = todoHeader

        binding.item = todoHeader
        binding.executePendingBindings()
    }

    private fun onCategoryItemClicked() {
        todoHeader?.let {
            listener.onTodoHeaderItemClicked(it.type)
        }
    }

    companion object {
        fun from(parent: ViewGroup, listener: OnTodoItemListener): TodoHeaderViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewTodoHeaderItemBinding.inflate(inflater, parent, false)
            return TodoHeaderViewHolder(binding, listener)
        }
    }
}

class TodoDiffUtilCallBack : DiffUtil.ItemCallback<Holder>() {
    override fun areItemsTheSame(oldItem: Holder, newItem: Holder) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Holder, newItem: Holder): Boolean {
        return if (oldItem is TodoHeader && newItem is TodoHeader) {
            oldItem.type == newItem.type
        } else if (oldItem is TodoWithCategory && newItem is TodoWithCategory) {
            newItem.todo.id == oldItem.todo.id
        } else {
            false
        }
    }
}

interface OnTodoItemListener {
    fun onTodoItemClicked(todo: Todo)
    fun onTodoItemDoneClicked(todo: Todo)
    fun onTodoHeaderItemClicked(type: TodoHeaderType)
}