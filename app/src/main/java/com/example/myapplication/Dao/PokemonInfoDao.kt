package com.example.myapplication.Dao


import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*
import com.example.myapplication.Data.PokemonInfo

@Dao
interface PokemonInfoDao {

        @Query("SELECT * FROM POKEMON_ENTITY")
        fun getAll(): List<PokemonInfo>

        @Query("SELECT * FROM POKEMON_ENTITY")
        fun getAllPaged(): PagingSource<Int, PokemonInfo>

        @Query("SELECT * FROM POKEMON_ENTITY WHERE favorite =:fav")
        fun getAllPagedFavorite(fav : Boolean ): PagingSource<Int, PokemonInfo>

        @Query("SELECT * FROM POKEMON_ENTITY WHERE id = :id")
        fun getById(id: Int): PokemonInfo

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(pokemon: PokemonInfo)

        @Update
        fun update(pokemon: PokemonInfo)

        @Delete
        fun delete(pokemon: PokemonInfo)


    }
