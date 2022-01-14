package com.example.myapplication.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonInstance {

    companion object {

        val baseUrl = "https://pokeapi.co/api/v2/"

        fun getPokemonInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

}