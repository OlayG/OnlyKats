package com.olayg.onlykats.util

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

fun ImageView.loadWithGlide(url: String?) {
    Log.e("lWG", "Inside of loadWithGlide for: $url")
    Glide.with(context)
        .load(url)
        .into(this)
}
fun ImageView.loadBreedsWithGlide(url: String?)
{
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ShapeableImageView.loadSingleImage(url: String?)
{
    Glide.with(context)
        .load(url)
        .into(this)
}