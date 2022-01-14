package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Paging.Adapter.FavoritePokemonAdapter
import com.example.myapplication.Paging.Adapter.PokemonAdapter
import com.example.myapplication.Paging.Model.MainActivityViewModel
import com.example.myapplication.Paging.Model.PokemonFavoriteViewModel
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private lateinit var recyclerViewAdapter: PokemonAdapter
    private lateinit var favoriteViewAdapter: FavoritePokemonAdapter


    lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = super.onCreateView(inflater, container, savedInstanceState)!!
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!DayPokemonFragment.isShow) {
            DayPokemonFragment().show(
                childFragmentManager, DayPokemonFragment.TAG
            )
            DayPokemonFragment.isShow = true
        }
    }


    override fun onStart() {
        super.onStart()
        initRecyclerView()
        initViewModel()
        initFavoriteModel()
    }


    private fun initFavoriteModel() {
        val viewModel = ViewModelProvider(this).get(PokemonFavoriteViewModel::class.java)
        favoriteViewAdapter = FavoritePokemonAdapter()
        favoriteViewAdapter.setOnItemClickListener(object : FavoritePokemonAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(myView.context, "Click on $position", Toast.LENGTH_SHORT)
                findNavController().navigate(
                    R.id.action_pokemonList_to_pokemonInfoFragment,
                    bundleOf(PokemonInfoFragment.pokemonId to favoriteViewAdapter.peek(position)?.id.toString())
                )
            }
        })
        fvRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(myView.context, LinearLayoutManager.HORIZONTAL, false)
            val decoration =
                DividerItemDecoration(myView.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }
        fvRecyclerView.adapter = favoriteViewAdapter
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.browseFavorite(true).collect {
                favoriteViewAdapter.submitData(it)
            }
        }
    }


    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(myView.context)
            val decoration =
                DividerItemDecoration(myView.context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            recyclerViewAdapter = PokemonAdapter()
            recyclerViewAdapter.setOnItemClickListener(object : PokemonAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(myView.context, "Click on $position", Toast.LENGTH_SHORT)
                    findNavController().navigate(
                        R.id.action_pokemonList_to_pokemonInfoFragment,
                        bundleOf(PokemonInfoFragment.pokemonUrl to recyclerViewAdapter.peek(position)?.url)
                    )
                }
            })

            adapter = recyclerViewAdapter
        }
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getListData().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }


}