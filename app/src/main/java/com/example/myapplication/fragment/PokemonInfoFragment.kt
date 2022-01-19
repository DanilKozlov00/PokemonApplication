package com.example.myapplication.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.api.PokemonInstance
import com.example.myapplication.api.PokemonService
import com.example.myapplication.data.PokemonInfo
import com.example.myapplication.paging.model.PokemonFavoriteViewModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_pokemon_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random


class PokemonInfoFragment : Fragment(R.layout.fragment_pokemon_info) {

    companion object {
        const val pokemonUrl = "pokemonUrl"
        const val pokemonId = "pokemonId"
    }

    lateinit var pokemon: PokemonInfo
    private lateinit var viewModel: PokemonFavoriteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id =
            if (arguments?.getString(pokemonUrl) != null) {
                arguments?.getString(pokemonUrl).toString().substringAfter("pokemon")
                    .replace("/", "").toInt()
            } else {
                arguments?.getString(pokemonId)?.toInt()
            }

        if (id != null) {
            getCurrentData(id)
        }

        val favoriteBtn = view.findViewById<Button>(R.id.favoriteBtn)
        favoriteBtn.setOnClickListener()
        {
            if (!pokemon.favorite) {
                pokemon.favorite = true
                viewModel.insert(pokemon)
                favoriteBtn.setBackgroundResource(R.drawable.favorite_star)
            } else {
                pokemon.favorite = false
                if (pokemon.userParams == null) {
                    viewModel.delete(pokemon)
                } else {
                    viewModel.update(pokemon)
                }
                favoriteBtn.setBackgroundResource(R.drawable.no_favorite_star)
            }
        }

        val changeButton = view.findViewById<Button>(R.id.changeInfoBtn)
        changeButton.setOnClickListener {
            if (pokemonInfoUserParams.isEnabled) {
                pokemonInfoUserParams.isEnabled = false
                changeButton.setBackgroundResource(R.drawable.edit_icon)
                if (pokemonInfoUserParams.text.isEmpty()) {
                    pokemonInfoUserParams.visibility = View.GONE
                } else {
                    pokemon.userParams = pokemonInfoUserParams.text.toString()
                    viewModel.insert(pokemon)
                }
            } else {
                pokemonInfoUserParams.visibility = View.VISIBLE
                pokemonInfoUserParams.isEnabled = true
                pokemonInfoUserParams.isFocusable = true

                changeButton.setBackgroundResource(R.drawable.done_icon)
            }
        }

        if (pokemonInfoUserParams.text.isEmpty()) {
            pokemonInfoUserParams.visibility = View.GONE
        }


    }


    private fun generateStats(pokemonInfo: PokemonInfo) {
        pokemonInfo.hp = Random.nextInt(300)
        pokemonInfo.attack = Random.nextInt(300)
        pokemonInfo.defense = Random.nextInt(300)
        pokemonInfo.speed = Random.nextInt(300)
    }


    @SuppressLint("SetTextI18n")
    private fun getCurrentData(id: Int) {

        val pokemonService: PokemonService = PokemonInstance.getPokemonInstance().create(
            PokemonService::class.java
        )

        viewModel = ViewModelProvider(this).get(PokemonFavoriteViewModel::class.java)

        if (viewModel.getById(id) != null) {
            pokemon = viewModel.getById(id)
        }
        var init: Boolean = false
        if (this::pokemon.isInitialized) {
            init = true
        }
        GlobalScope.launch(Dispatchers.IO) {
            if (!init)
                pokemon = pokemonService.getSinglePokemon(id)

            generateStats(pokemon)

            withContext(Dispatchers.Main) {

                if (pokemon.userParams != null) {
                    pokemonInfoUserParams.visibility = View.VISIBLE
                    pokemonInfoUserParams.setText(pokemon.userParams)
                }
                if (pokemon.favorite) {
                    favoriteBtn.setBackgroundResource(R.drawable.favorite_star)
                }

                pokemonInfoName.text = pokemon.name
                pokemonInfoWeight.text = "Weight " + pokemon.weight / 10 + "kg"
                pokemonInfoHeight.text = "Height " + (pokemon.height.toDouble() / 10) + "m"
                pokemonInfoExp.text = "Exp " + pokemon.base_experience

                Glide.with(pokemonInfoImage)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png")
                    .circleCrop()
                    .into(pokemonInfoImage)
            }
        }
    }

}