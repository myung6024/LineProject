package com.runeanim.lineproject.ui.addeditmemo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.runeanim.lineproject.model.AttachedImage

@BindingAdapter("app:thumbnailImage")
fun ImageView.setProductImage(path: String?) {
    path?.let {
        Glide.with(context)
            .load(path)
            .into(this)
    }
}

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<AttachedImage>?) {
    items?.let {
        (listView.adapter as AttachedImageAdapter).submitList(items)
    }
}