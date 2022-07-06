package com.srdroid.memedb.core

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.srdroid.memedb.R

@BindingAdapter("urlToImage")
fun urlToImage(view: ImageView, s: String?) {
    val options = RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)
    Glide.with(view).setDefaultRequestOptions(options).load(s ?: "").into(view)
}

@BindingAdapter("imageRatio")
fun setConstraintRatio(view: ImageView, ratio: String) {
    val constraintLayout = view.parent as ConstraintLayout
    val constraintSet = ConstraintSet()
    constraintSet.clone(constraintLayout)
    constraintSet.setDimensionRatio(view.id, ratio)
    constraintSet.applyTo(constraintLayout)
}