package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewTodoItemBinding
import com.easycodingstudio.todou.model.Category
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.ui.todou.TodouViewModel

class TodoAdapter(
    private val category: Category,
    private val viewModel: TodouViewModel
) : ListAdapter<Todo, TodoViewHolder>(TodoDiffUtilCallBack()) {
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, category) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TodoViewHolder.from(parent, viewModel)
}

class TodoViewHolder private constructor(
    private val binding: RecyclerviewTodoItemBinding,
    private val viewModel: TodouViewModel
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

    fun bind(todo: Todo, category: Category) {
        // set for listener
        localTodo = todo

        binding.todo = todo
        binding.category = category
        binding.executePendingBindings()
    }

    private fun onTodoItemClicked() {
        localTodo?.let {
            viewModel.onTodoItemClicked(it)
        }
    }

    private fun onTodoItemDoneClicked() {
        localTodo?.let {
            val updatedTodo = it.copy(isCompleted = !it.isCompleted)
            viewModel.onTodoItemDoneClicked(updatedTodo)
        }
    }

    companion object {
        fun from(parent: ViewGroup, viewModel: TodouViewModel): TodoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewTodoItemBinding.inflate(layoutInflater, parent, false)
            return TodoViewHolder(binding, viewModel)
        }
    }
}

class TodoDiffUtilCallBack : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem.id == newItem.id
}