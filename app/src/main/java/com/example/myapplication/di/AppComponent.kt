package com.example.myapplication.di

import com.example.myapplication.paging.model.PokemonFavoriteViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {


    fun inject(pokemonActivityViewModel: PokemonFavoriteViewModel) {

    }
}