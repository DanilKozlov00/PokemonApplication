package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    var speed: Int?
)