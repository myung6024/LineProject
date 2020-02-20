package com.runeanim.lineproject.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.lineproject.databinding.MemoItemBinding
import com.runeanim.lineproject.model.MemoEntity

class MemoListAdapter(private val viewmodel: MainViewModel) :
    ListAdapter<MemoEntity, MemoListAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(viewmodel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: MemoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewmodel: MainViewModel, item: MemoEntity) {
            binding.viewmodel = viewmodel
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MemoItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback : DiffUtil.ItemCallback<MemoEntity>() {
    override fun areItemsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MemoEntity, newItem: MemoEntity): Boolean {
        return oldItem == newItem
    }
}
