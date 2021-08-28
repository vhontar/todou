package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewArchiveDateItemBinding
import com.easycodingstudio.todou.databinding.RecyclerviewArchiveTodoItemBinding
import com.easycodingstudio.todou.model.DateWithTodos
import com.easycodingstudio.todou.model.Todo
import com.easycodingstudio.todou.ui.archive.ArchiveViewModel

class ArchiveAdapter(
    private val viewModel: ArchiveViewModel
): ListAdapter<DateWithTodos, RecyclerView.ViewHolder>(CompletedTodoDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class ArchiveDateViewHolder(
    private val binding: RecyclerviewArchiveDateItemBinding
): RecyclerView.ViewHolder(binding.root) {


    companion object {
        fun from(parent: ViewGroup): ArchiveDateViewHolder  {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewArchiveDateItemBinding.inflate(inflater, parent, false)
            return ArchiveDateViewHolder(binding)
        }
    }
}

class ArchiveTodoViewHolder(
    private val binding: RecyclerviewArchiveTodoItemBinding,
    private val viewModel: ArchiveViewModel
): RecyclerView.ViewHolder(binding.root) {

    fun bind(todo: Todo) {

    }

    companion object {
        fun from(parent: ViewGroup, viewModel: ArchiveViewModel): ArchiveTodoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewArchiveTodoItemBinding.inflate(inflater, parent, false)
            return ArchiveTodoViewHolder(binding, viewModel)
        }
    }

}

class CompletedTodoDiffUtilCallback: DiffUtil.ItemCallback<DateWithTodos>() {
    override fun areContentsTheSame(oldItem: DateWithTodos, newItem: DateWithTodos) = oldItem == newItem
    override fun areItemsTheSame(oldItem: DateWithTodos, newItem: DateWithTodos) =
        oldItem.date == newItem.date
}