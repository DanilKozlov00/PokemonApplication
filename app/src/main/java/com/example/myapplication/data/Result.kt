package com.example.myapplication.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class Result(

    val name: String,
    val url: String,
    var pokemonImage : String?

) {
    companion object {
        @JvmStatic
        @BindingAdapter("app:singlePokemonImage")
        fun loadSingleImage(view: ImageView, url: String?) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }

    }
}