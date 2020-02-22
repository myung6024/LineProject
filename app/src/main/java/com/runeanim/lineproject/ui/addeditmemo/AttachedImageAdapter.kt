package com.runeanim.lineproject.ui.addeditmemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.lineproject.base.AttachedImageClickListener
import com.runeanim.lineproject.databinding.AttachedImageItemBinding
import com.runeanim.lineproject.data.model.AttachedImage

class AttachedImageAdapter(val listener: AttachedImageClickListener, val isEditMode: Boolean = false) :
    ListAdapter<AttachedImage, AttachedImageAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item, isEditMode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: AttachedImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: AttachedImageClickListener, item: AttachedImage, isEditMode: Boolean) {
            binding.isEditMode = isEditMode
            binding.listener = listener
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AttachedImageItemBinding.inflate(layoutInflater, parent, false)

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
class TaskDiffCallback : DiffUtil.ItemCallback<AttachedImage>() {
    override fun areItemsTheSame(oldItem: AttachedImage, newItem: AttachedImage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AttachedImage, newItem: AttachedImage): Boolean {
        return oldItem.path == newItem.path
    }
}
