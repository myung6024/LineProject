package com.runeanim.lineproject.ui.imagedetail

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

@BindingAdapter("app:image")
fun PhotoView.setImage(path: String?) {
    path?.let {
        Glide.with(context)
            .load(path)
            .into(this)
    }
}