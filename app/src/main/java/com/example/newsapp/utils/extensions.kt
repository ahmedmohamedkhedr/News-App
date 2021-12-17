package com.example.newsapp.utils

import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        Picasso.get().load(Uri.parse(it)).into(this)
    }
}