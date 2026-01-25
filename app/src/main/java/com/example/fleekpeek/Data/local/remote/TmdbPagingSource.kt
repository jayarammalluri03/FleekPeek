package com.example.fleekpeek.Data.local.remote
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.fleekpeek.Data.local.remote.model.TMDBItem
import com.example.fleekpeek.Data.local.remote.model.TMDBResponse


class TmdbPagingSource(private val tmdbApis: TmdbApis, private val searchQuery: String):
    PagingSource<Int, TMDBItem>() {


    override fun getRefreshKey(state: PagingState<Int, TMDBItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TMDBItem> {
        val page= params.key ?: 1
        return try{
            val res : TMDBResponse = tmdbApis.searchMulti(query = searchQuery, page = page)
            val items = res.results
            val totalpages= res.total_pages
            val nextKey = if (page >= totalpages) null else page + 1
            LoadResult.Page(data = items, prevKey = if (page == 1) null else page - 1, nextKey = nextKey)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}