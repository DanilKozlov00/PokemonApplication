package com.example.myapplication.Api

import com.example.myapplication.Data.Pokemon
import com.example.myapplication.Data.PokemonInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokemonService {

    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Pokemon

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int
    ): PokemonInfo

}