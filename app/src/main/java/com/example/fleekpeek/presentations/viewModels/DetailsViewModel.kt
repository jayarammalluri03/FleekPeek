package com.example.fleekpeek.presentations.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.Data.local.mapper.toTMDBItem
import com.example.fleekpeek.domain.use_cases.favorites_useCases.FavUseCases
import com.example.fleekpeek.domain.use_cases.responseUsecases.DetailsMovieUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.DetailsShowUseCase
import com.example.fleekpeek.presentations.ui.details.DetailsEvents
import com.example.fleekpeek.remote.MovieDetails
import com.example.fleekpeek.remote.TvDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class MediaType { MOVIE, TV }

data class DetailsUiState<T>(
    val items: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMoviesDetails: DetailsMovieUseCase,
    private val getTvDetails: DetailsShowUseCase,
    private val savedState: SavedStateHandle,
    private val favUseCase: FavUseCases
):ViewModel() {


    private val _detailsMovie= MutableStateFlow(DetailsUiState<MovieDetails>(isLoading = true))
    val detailsMovie: StateFlow<DetailsUiState<MovieDetails>> = _detailsMovie

    private val _detailsTv= MutableStateFlow(DetailsUiState<TvDetails>(isLoading = true))
    val detailsTv: StateFlow<DetailsUiState<TvDetails>> = _detailsTv

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite


    init {
        Log.e("detailsviewmodel", "init")
    }

    private fun observeFavorite(id: Int) {
        viewModelScope.launch {
            favUseCase.isFavorite(id).collect { isFav ->
                _isFavorite.value = isFav
            }
        }
    }


    fun onEvent(event: DetailsEvents) {
        when (event) {
            is DetailsEvents.getIdMediaType -> {
                // loadDetails already uses viewModelScope.launch inside, so this is fine
                loadDetails(event.mediaType, event.id)
                observeFavorite(event.id)
            }

            is DetailsEvents.toggleFavorite -> {
                viewModelScope.launch {
                    if (event.isFav) {
                        favUseCase.addFavorite(event.data.toTMDBItem())
                    } else {
                        favUseCase.removeFavorite(event.data.toTMDBItem())
                    }
                }
            }
        }
    }


    private fun loadDetails(type: String, id: Int) {
        viewModelScope.launch {
            if(type == "movie"){
                _detailsMovie.value= _detailsMovie.value.copy(isLoading =  true, error = null)
                try {
                   val details= getMoviesDetails(id)
                    _detailsMovie.value= DetailsUiState(items = details, isLoading = false)
                }catch (e: Exception){
                    _detailsMovie.value = DetailsUiState(items = null, isLoading = false, error = e.message)
                }
            }else{
                _detailsTv.value= _detailsTv.value.copy(isLoading =  true, error = null)
                try {
                    val details= getTvDetails(id)
                    _detailsTv.value= DetailsUiState(items = details, isLoading = false)

                }catch (e: Exception){
                    _detailsMovie.value = DetailsUiState(items = null, isLoading = false, error = e.message)
                }
            }
        }
    }




}