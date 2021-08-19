package com.easycodingstudio.todou.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.easycodingstudio.todou.databinding.RecyclerviewColorItemBinding
import com.easycodingstudio.todou.model.Color
import com.easycodingstudio.todou.ui.category.CategoryViewModel

class ColorAdapter(
    private val viewModel: CategoryViewModel
): ListAdapter<Color, ColorViewHolder>(ColorDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ColorViewHolder.from(parent, viewModel)
    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) = holder.bind(getItem(position))
}

class ColorViewHolder(
    private val binding: RecyclerviewColorItemBinding,
    private val viewModel: CategoryViewModel
): RecyclerView.ViewHolder(binding.root) {

    private var localColor: Color? = null

    init {
        /**
         * It's cheaper to subscribe to listeners only for created view holders and reuse them when new
         * data binding to the created view holder
         * */
        binding.ivColor.setOnClickListener { onColorClicked() }
    }

    fun bind(color: Color) {
        localColor = color

        binding.color = color
        binding.executePendingBindings()
    }

    private fun onColorClicked() {
        localColor?.let {
            viewModel.onColorClicked(color = it)
        }
    }

    companion object {
        fun from(parent: ViewGroup, viewModel: CategoryViewModel): ColorViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = RecyclerviewColorItemBinding.inflate(inflater, parent, false)
            return ColorViewHolder(binding, viewModel)
        }
    }
}

class ColorDiffUtilCallback: DiffUtil.ItemCallback<Color>() {
    override fun areItemsTheSame(oldItem: Color, newItem: Color) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Color, newItem: Color) = oldItem.id == newItem.id
}