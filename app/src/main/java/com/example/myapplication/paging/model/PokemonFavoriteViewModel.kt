package com.example.myapplication.paging.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.api.PokemonController
import com.example.myapplication.api.PokemonService
import com.example.myapplication.dao.AppDataBase
import com.example.myapplication.dao.PokemonRepository
import com.example.myapplication.data.PokemonInfo
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_day_pokemon.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


class PokemonFavoriteViewModel constructor(application: Application) :
    AndroidViewModel(application) {

    private val repo: PokemonRepository
    private val db: AppDataBase = AppDataBase.getAppDataBaseInstance(application)
    private val pokemonService: PokemonService = PokemonController.getPokemonInstance().create(
        PokemonService::class.java
    )

    init {
        repo = PokemonRepository(db.getPokemonDao())
    }

    suspend fun getPokemon() = coroutineScope {
        val pokemon = async { createPokemon() }
        pokemon.await()
    }

    fun browseFavorite(fav: Boolean): Flow<PagingData<PokemonInfo>> {
        return Pager(PagingConfig(pageSize = 10)) {
            repo.getAllFavorite(fav)
        }.flow.cachedIn(viewModelScope)
    }

    fun insert(pokemonInfo: PokemonInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(pokemonInfo)
        }
    }

    fun getById(id: Int): PokemonInfo? {
        return repo.getById(id)
    }

    fun delete(pokemonInfo: PokemonInfo) {
        repo.delete(pokemonInfo)
    }

    fun update(pokemonInfo: PokemonInfo) {
        repo.update(pokemonInfo)
    }

    private suspend fun createPokemon(): PokemonInfo = withContext(Dispatchers.IO) {
        var id = randomId()
        var currentPokemon = this@PokemonFavoriteViewModel.getById(id)
        while (currentPokemon != null && !currentPokemon.favorite) {
            id = randomId()
            currentPokemon = this@PokemonFavoriteViewModel.getById(id)
        }
        currentPokemon = pokemonService.getSinglePokemon(id)
        currentPokemon.pokemonImage =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
        return@withContext currentPokemon
    }

    private fun randomId(): Int {
        return (0..898).random()
    }

    fun favoriteBtnClick(pokemon: PokemonInfo) {
        pokemon.favorite = true
        if (this.getById(pokemon.id) != null) {
            this.update(pokemon)
        } else {
            this.insert(pokemon)
        }
    }

}



