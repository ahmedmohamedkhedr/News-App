package com.example.newsapp.utils

import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.newsapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(imageUrl: String?) {
    imageUrl?.let {
        Picasso.get().load(Uri.parse(it)).into(this)
    }
}

fun FragmentActivity.moveTo(fragment: Fragment, withContainerId: Int) {
    supportFragmentManager.beginTransaction().setCustomAnimations(
        R.anim.slide_in_right_to_left,
        R.anim.slide_out_right_to_left,
        R.anim.slide_in_left_to_right,
        R.anim.slide_out_left_to_right,
    ).replace(withContainerId, fragment)
        .addToBackStack(fragment::class.java.name).commit()
}