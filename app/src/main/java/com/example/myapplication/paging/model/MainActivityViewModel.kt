package com.example.myapplication.paging.model

import android.app.Application
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.api.PokemonController
import com.example.myapplication.api.PokemonService
import com.example.myapplication.data.Result
import com.example.myapplication.fragment.PokemonInfoFragment
import com.example.myapplication.paging.PokemonPagingSource
import com.example.myapplication.paging.adapter.PokemonAdapter
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel constructor(application: Application) :
    AndroidViewModel(application) {


    var pokemonService: PokemonService =
        PokemonController.getPokemonInstance().create(PokemonService::class.java)

    fun getListData(): Flow<PagingData<Result>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { PokemonPagingSource(pokemonService) }).flow.cachedIn(
            viewModelScope
        )
    }



}