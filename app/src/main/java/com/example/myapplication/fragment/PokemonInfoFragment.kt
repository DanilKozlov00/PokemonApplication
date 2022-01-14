package com.example.myapplication.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.Api.PokemonInstance
import com.example.myapplication.Api.PokemonService
import com.example.myapplication.Data.PokemonInfo
import com.example.myapplication.Paging.Model.PokemonFavoriteViewModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_pokemon_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        favoriteBtn.setOnClickListener {

            if (favoriteBtn.text == "FV") {
                pokemon.favorite = true
                if (viewModel.getById(pokemon.id) != null) {
                    viewModel.update(pokemon)
                } else {
                    viewModel.insert(pokemon)
                }
                favoriteBtn.text = "UF"
            } else {
                if (pokemon.userParams != null) {
                    pokemon.favorite = false
                    viewModel.update(pokemon)
                } else {
                    viewModel.delete(pokemon)
                }
                favoriteBtn.text = "FV"
            }

        }

        val changeButton = view.findViewById<Button>(R.id.changeInfoBtn)
        changeButton.setOnClickListener {
            if (changeButton.text == "CH") {
                userParams.visibility = View.VISIBLE
                userParams.isEnabled = true
                changeButton.text = "OK"
            } else {
                changeButton.text = "CH"
                pokemon.userParams = userParams.text.toString()
                if (viewModel.getById(pokemon.id) != null) {
                    viewModel.update(pokemon)
                } else {
                    viewModel.insert(pokemon)
                }
                userParams.isEnabled = false
            }
        }

        if (userParams.text.isEmpty()) {
            userParams.visibility = View.GONE
        }


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


            withContext(Dispatchers.Main) {

                if (pokemon.userParams != null) {
                    userParams.visibility = View.VISIBLE
                    userParams.setText(pokemon.userParams)
                }
                if (pokemon.favorite) {
                    favoriteBtn.setText("UF")
                }

                pokemonInfoName.text = pokemon.name
                weight.text = "Weight " + pokemon.weight / 10 + "kg"
                height.text = "Height " + pokemon.height / 10 + "m"
                base_experience.text = "Exp " + pokemon.base_experience

                Glide.with(imageInfo)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png")
                    .circleCrop()
                    .into(imageInfo)
            }
        }
    }

}