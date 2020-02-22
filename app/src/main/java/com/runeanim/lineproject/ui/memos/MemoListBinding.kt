package com.runeanim.lineproject.ui.memos

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.runeanim.lineproject.data.model.Memo
import java.text.SimpleDateFormat

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Memo>?) {
    items?.let {
        (listView.adapter as MemoListAdapter).submitList(items)
    }
}

@BindingAdapter("app:timeText", "app:dateFormat")
fun TextView.setTimeText(milliSecond: Long?, dateFormat: SimpleDateFormat) {
    milliSecond?.let {
        text = dateFormat.format(milliSecond)
    }
}