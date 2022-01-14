package com.example.myapplication.Paging.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Data.Result
import com.example.myapplication.R

class PokemonAdapter :
    PagingDataAdapter<Result, PokemonAdapter.MyViewHolder>(DiffUtilCallBack()) {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onBindViewHolder(holder: PokemonAdapter.MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)

        return MyViewHolder(inflater, mListener)
    }


    class MyViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val pokemonName: TextView = view.findViewById(R.id.pokemonName)

        fun bind(data: Result) {
            pokemonName.text = data.name
            val id = data.url.substringAfter("pokemon").replace("/", "").toInt()
            Glide.with(imageView)
                .load(getPicUrl(id))
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


    class DiffUtilCallBack : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name == newItem.name

        }

    }


}