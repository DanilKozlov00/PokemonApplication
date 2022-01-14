package com.example.myapplication.Paging.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.PokemonInfo
import com.example.myapplication.R

class FavoritePokemonAdapter :
    PagingDataAdapter<PokemonInfo, FavoritePokemonAdapter.PersonViewHolder>(PersonDiffCallback()) {


    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onBindViewHolder(holderPerson: PersonViewHolder, position: Int) {
        var person = getItem(position)

        if (person != null) {
            holderPerson.bind(person)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritePokemonAdapter.PersonViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_pokemon_favorite, parent, false)

        return FavoritePokemonAdapter.PersonViewHolder(inflater, mListener)
    }


    class PersonViewHolder(view: View, listener: FavoritePokemonAdapter.onItemClickListener) :
        RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.favoritePokemonImageView)

        fun bind(data: PokemonInfo) {
            Glide.with(imageView)
                .load(getPicUrl(data.id))
                .circleCrop()
                .into(imageView)
        }

        fun getPicUrl(id: Int): String {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
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