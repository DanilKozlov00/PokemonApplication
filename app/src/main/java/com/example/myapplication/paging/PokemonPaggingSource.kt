package com.example.myapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.api.PokemonService
import com.example.myapplication.data.Result
import java.io.IOException

class PokemonPaggingSource(val apiService: PokemonService) : PagingSource<Int, Result>() {


    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val offset = params.key ?: 0

        val loadSize = params.loadSize
        return try {
            val data = apiService.getPokemons(loadSize, offset)

            val filteredData = data.results

            LoadResult.Page(
                data = filteredData,
                prevKey = if (offset == 1) null else offset - loadSize,
                nextKey = if (data.next == null) null else offset + loadSize
            )
        } catch (t: Throwable) {
            var exception = t

            if (t is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }
    }


}