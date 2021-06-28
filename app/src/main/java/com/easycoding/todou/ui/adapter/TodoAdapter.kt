package com.easycoding.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.todou.databinding.RecyclerviewTodoItemBinding
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.Todo

class TodoAdapter(
    private val category: Category,
    private val listener: TodoListener? = null
) : ListAdapter<Todo, TodoViewHolder>(TodoDiffUtilCallBack()) {
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, category) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TodoViewHolder.from(parent, listener)
}

class TodoViewHolder private constructor(
    private val binding: RecyclerviewTodoItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var listener: TodoListener? = null

    init {
        binding.rlTodoRoot.setOnClickListener { listener?.todoItemClicked() }
        // rethink the solution of changing image and sending the event to listener
        binding.ivTodoDone.setOnClickListener {
            val isDone = binding.todo?.isDone ?: false
            listener?.todoItemDoneClicked(isDone)
            binding.todo = binding.todo?.copy(isDone = !isDone)
            binding.executePendingBindings()
        }
    }

    fun bind(todo: Todo, category: Category) {
        binding.todo = todo
        binding.category = category
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, listener: TodoListener?): TodoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewTodoItemBinding.inflate(layoutInflater, parent, false)
            val viewHolder = TodoViewHolder(binding)
            viewHolder.listener = listener
            return TodoViewHolder(binding)
        }
    }
}

interface TodoListener {
    fun todoItemClicked()
    fun todoItemDoneClicked(isDone: Boolean)
}

class TodoDiffUtilCallBack : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem.id == newItem.id
}