package com.dog.app.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageBindingAdapter {
companion object {
    @JvmStatic
    @BindingAdapter("android:loadImage")
    fun loadImage(imageView: ImageView, imageUri: String?) {
        Glide.with(imageView.context)
            .load(imageUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}
}