package com.example.myapplication.fragment

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.api.PokemonInstance
import com.example.myapplication.api.PokemonService
import com.example.myapplication.data.PokemonInfo
import com.example.myapplication.paging.model.PokemonFavoriteViewModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_day_pokemon.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DayPokemonFragment : DialogFragment(R.layout.fragment_day_pokemon) {

    companion object {
        const val TAG = "PokemonDay"
        var isShow: Boolean = false
    }

    lateinit var pokemon: PokemonInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentData()
        val button = view.findViewById<Button>(R.id.addToFavoriteBtn)
        button.setOnClickListener {
            val viewModel = ViewModelProvider(this).get(PokemonFavoriteViewModel::class.java)
            pokemon.favorite = true
            if (viewModel.getById(pokemon.id) != null) {
                viewModel.update(pokemon)
            } else {
                viewModel.insert(pokemon)
            }
            dialog?.dismiss()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(85)
    }

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), (percentWidth.toInt() * 1.5).toInt())
    }


    @SuppressLint("SetTextI18n")
    private fun getCurrentData() {

        val pokemonService: PokemonService = PokemonInstance.getPokemonInstance().create(
            PokemonService::class.java
        )

        val viewModel = ViewModelProvider(this).get(PokemonFavoriteViewModel::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            var id = randomId()
            while (viewModel.getById(id) != null) {
                id = randomId()
            }
            pokemon = pokemonService.getSinglePokemon(id)



            withContext(Dispatchers.Main) {
                pokemonDayName.text = pokemon.name
                pokemonWeight.text = "Weight " + pokemon.weight / 10 + "kg"
                pokemonHeight.text = "Height " + (pokemon.height.toDouble() / 10) + "m"
                pokemonExp.text = "Exp " + pokemon.base_experience

                Glide.with(pokemonDayImage)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png")
                    .circleCrop()
                    .into(pokemonDayImage)
            }
        }
    }


    private fun randomId(): Int {
        return (0..898).random()
    }

}