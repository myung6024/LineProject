package com.runeanim.lineproject.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.lineproject.model.Memo

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Memo>?) {
    items?.let {
        (listView.adapter as MemoListAdapter).submitList(items)
    }
}