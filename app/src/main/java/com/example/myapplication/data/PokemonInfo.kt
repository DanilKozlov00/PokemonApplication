package com.example.myapplication.data

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@Entity(tableName = "pokemon_entity")
data class PokemonInfo(

    var name: String,
    val weight: Int,
    val height: Int,
    @PrimaryKey(autoGenerate = false) val id: Int,
    var favorite: Boolean = false,
    val base_experience: Int,
    var userParams: String?,
    var hp: Int?,
    var attack: Int?,
    var defense: Int?,
    var speed: Int?,
    var pokemonImage: String?
) {

    companion object {
        @JvmStatic
        @BindingAdapter("app:pokemonImage")
        fun loadImage(view: ImageView, url: String?) {
            Glide.with(view.context)
                .load(url)
                .into(view)
        }

    }

    fun getWeightString(): String = String.format("Weight %.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("Height %.1f M", height.toFloat() / 10)
    fun getExpString(): String = "Exp $base_experience"


}