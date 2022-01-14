package com.example.myapplication.Paging.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.Dao.AppDataBase
import com.example.myapplication.Dao.PokemonRepository
import com.example.myapplication.Data.PokemonInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class PokemonFavoriteViewModel constructor(application: Application) :
    AndroidViewModel(application) {

    private val repo: PokemonRepository
    private val db: AppDataBase

    init {
        db = AppDataBase.getAppDataBaseInstance(application)
        repo = PokemonRepository(db.getPokemonDao())
    }

    fun browse(): Flow<PagingData<PokemonInfo>> {
        return Pager(PagingConfig(pageSize = 10)) {
            repo.getAll()
        }.flow.cachedIn(viewModelScope)
    }

    fun browseFavorite(fav : Boolean): Flow<PagingData<PokemonInfo>> {
        return Pager(PagingConfig(pageSize = 10)) {
            repo.getAllFavorite(fav)
        }.flow.cachedIn(viewModelScope)
    }

    fun insert(pokemonInfo: PokemonInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(pokemonInfo)
        }
    }

    fun getById(id: Int) : PokemonInfo {
        return repo.getById(id)
    }

    fun delete(pokemonInfo: PokemonInfo) {
        repo.delete(pokemonInfo)
    }

    fun update (pokemonInfo: PokemonInfo) {
        repo.update(pokemonInfo)
    }
}


