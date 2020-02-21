package com.runeanim.lineproject.ui.memos

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.lineproject.model.Memo
import java.text.SimpleDateFormat

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Memo>?) {
    items?.let {
        (listView.adapter as MemoListAdapter).submitList(items)
    }
}

@BindingAdapter("app:timeText")
fun TextView.setTimeText(milliSecond: Long?) {
    milliSecond?.let {
        val sdf = SimpleDateFormat("yyyy년 M월 d일 H시 m분")
        text = sdf.format(milliSecond)
    }
}