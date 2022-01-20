package com.example.myapplication.paging.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.PokemonInfo
import com.example.myapplication.databinding.FragmentPokemonFavoriteBinding

class FavoritePokemonAdapter :
    PagingDataAdapter<PokemonInfo, FavoritePokemonAdapter.FavoriteViewHolder>(PersonDiffCallback()) {


    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onBindViewHolder(holderFavorite: FavoriteViewHolder, position: Int) {
        holderFavorite.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritePokemonAdapter.FavoriteViewHolder {
        return FavoritePokemonAdapter.FavoriteViewHolder(
            FragmentPokemonFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }


    class FavoriteViewHolder(
        private val binding: FragmentPokemonFavoriteBinding,
        listener: FavoritePokemonAdapter.onItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PokemonInfo) {
            binding.pokemon = data
            data.pokemonImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${data.id}.png"
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    class PersonDiffCallback : DiffUtil.ItemCallback<PokemonInfo>() {

        override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
            return oldItem == newItem
        }
    }
}