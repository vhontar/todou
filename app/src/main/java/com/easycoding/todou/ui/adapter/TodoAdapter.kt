package com.easycoding.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycoding.todou.databinding.RecyclerviewTodoItemBinding
import com.easycoding.todou.model.Category
import com.easycoding.todou.model.Todo

class TodoAdapter(
    private val category: Category,
    private val listener: OnTodoClickListener? = null
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

    private var listener: OnTodoClickListener? = null
    private var localTodo: Todo? = null

    init {
        /**
         * It's cheaper to subscribe to listeners only for created view holders and reuse them when new
         * data binding to the created view holder
         * */
        binding.rlTodoRoot.setOnClickListener {
            localTodo?.let { listener?.onTodoItemClicked(it) }
        }
        binding.ivTodoDone.setOnClickListener {
            localTodo?.let {
                val updatedTodo = it.copy(isDone = !it.isDone)
                listener?.onTodoItemDoneClicked(updatedTodo)
            }
        }
    }

    fun bind(todo: Todo, category: Category) {
        // set for listener
        localTodo = todo

        binding.todo = todo
        binding.category = category
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup, listener: OnTodoClickListener?): TodoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewTodoItemBinding.inflate(layoutInflater, parent, false)
            val viewHolder = TodoViewHolder(binding)
            viewHolder.listener = listener
            return TodoViewHolder(binding)
        }
    }
}

interface OnTodoClickListener {
    fun onTodoItemClicked(todo: Todo)
    fun onTodoItemDoneClicked(todo: Todo)
}

class TodoDiffUtilCallBack : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem.id == newItem.id
}