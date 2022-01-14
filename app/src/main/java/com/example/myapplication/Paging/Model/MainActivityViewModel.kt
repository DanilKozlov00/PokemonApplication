package com.example.myapplication.Paging.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.Api.PokemonInstance
import com.example.myapplication.Api.PokemonService
import com.example.myapplication.Data.Result
import com.example.myapplication.Paging.PokemonPaggingSource
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel : ViewModel() {

    var pokemonService: PokemonService =
        PokemonInstance.getPokemonInstance().create(PokemonService::class.java)

    fun getListData(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { PokemonPaggingSource(pokemonService) }).flow.cachedIn(
            viewModelScope
        )
    }
}