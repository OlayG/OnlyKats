package com.olayg.onlykats.util

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadWithGlide(url: String?) {
    Log.e("lWG", "Inside of loadWithGlide for: $url")
    Glide.with(context)
        .load(url)
        .into(this)
}
fun ImageView.loadBreedsWithGlide(name: String?,url: String?)
{
    Glide.with(context)
        .load(url)
        .load(name)
        .into(this)
}