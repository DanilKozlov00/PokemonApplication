package com.example.myapplication.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.data.Result

import com.example.myapplication.databinding.PokemonItemBinding

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
        return MyViewHolder(
            PokemonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }


    class MyViewHolder(private val binding: PokemonItemBinding, listener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Result) {
            binding.pokemonResult = data
            val index = data.url.split("/".toRegex()).dropLast(1).last()
            data.pokemonImage =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
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