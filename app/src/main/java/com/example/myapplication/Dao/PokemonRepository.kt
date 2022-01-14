package com.example.myapplication.Dao

import androidx.paging.PagingSource
import com.example.myapplication.Data.PokemonInfo

class PokemonRepository (private val pokemonInfoDao: PokemonInfoDao) {


    fun getAll() : PagingSource<Int,PokemonInfo> {
        return pokemonInfoDao.getAllPaged()
    }

    fun getAllFavorite(fav : Boolean) : PagingSource<Int,PokemonInfo> {
        return pokemonInfoDao.getAllPagedFavorite(fav)
    }

    fun insert (pokemonInfo: PokemonInfo) {
        pokemonInfoDao.insert(pokemonInfo)
    }

    fun getById (id : Int) : PokemonInfo {
       return pokemonInfoDao.getById(id)
    }

    fun delete (pokemonInfo: PokemonInfo )  {
        pokemonInfoDao.delete(pokemonInfo)
    }

    fun update (pokemonInfo: PokemonInfo) {
        pokemonInfoDao.update(pokemonInfo)
    }


}