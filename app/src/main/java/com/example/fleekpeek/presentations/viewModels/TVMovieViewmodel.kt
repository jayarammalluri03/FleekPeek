package com.example.fleekpeek.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetAnimeTvShowsUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetBollywoodActionMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetComfortTvShowsUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetCrimeMysteryMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetDarkThrillerTvUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetHiddenGemsMoviesUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetKDramaTvShowsUseCase
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetSciFiMoviesUseCase
import com.example.fleekpeek.presentations.ui.tvmovie.TVMovieEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVMovieViewmodel @Inject constructor(
    val getSciFiMovies: GetSciFiMoviesUseCase,
    val getBollywoodActionMovies: GetBollywoodActionMoviesUseCase,
    val getCrimeMysteryMovies: GetCrimeMysteryMoviesUseCase,
    val getHiddenGemsMovies: GetHiddenGemsMoviesUseCase,
    val getDarkThrillerTv: GetDarkThrillerTvUseCase,
    val getAnimeTvShows: GetAnimeTvShowsUseCase,
    val getComfortTvShows: GetComfortTvShowsUseCase,
    val getKDramaTvShows: GetKDramaTvShowsUseCase
): ViewModel() {


    private val _sciFi= MutableStateFlow(SectionUiState(isLoading = true))
    val sciFi: MutableStateFlow<SectionUiState> = _sciFi

    private val _bollywoodAction= MutableStateFlow(SectionUiState(isLoading = true))
    val bollywoodAction: MutableStateFlow<SectionUiState> = _bollywoodAction

    private val _crimeMystery= MutableStateFlow(SectionUiState(isLoading = true))
    val crimeMystery: MutableStateFlow<SectionUiState> = _crimeMystery

    private val _hideenGemsMovies= MutableStateFlow(SectionUiState(isLoading = true))
    val hideenGemsMovies: MutableStateFlow<SectionUiState> = _hideenGemsMovies

    private val _darkThrillerTv= MutableStateFlow(SectionUiState(isLoading = true))
    val darkThrillerTv: MutableStateFlow<SectionUiState> = _darkThrillerTv

    private val _animeTvShows= MutableStateFlow(SectionUiState(isLoading = true))
    val animeTvShows: MutableStateFlow<SectionUiState> = _animeTvShows

    private val _comfortTvShows= MutableStateFlow(SectionUiState(isLoading = true))
    val comfortTvShows: MutableStateFlow<SectionUiState> = _comfortTvShows


    private val _kDramaTvShows= MutableStateFlow(SectionUiState(isLoading = true))
    val kDramaTvShows: MutableStateFlow<SectionUiState> = _kDramaTvShows

    init {
        loadDarkThrillerTv()
        loadAnimeTvShows()
        loadComfortTvShows()
        loadKDramaTvShows()
    }

    fun onEvent(event: TVMovieEvent){
        when(event){
            is TVMovieEvent.loadData -> {
                if(event.isMovie){
                    loadSciFi()
                   loadBollywoodAction()
                   loadCrimeMystery()
                    loadHiddenGemsMovies()
                }else{
                    loadDarkThrillerTv()
                    loadAnimeTvShows()
                   loadComfortTvShows()
                    loadKDramaTvShows()
                }

            }
        }
    }


    fun loadSciFi(page: Int = 1) {
        viewModelScope.launch {
            _sciFi.value = _sciFi.value.copy(isLoading = true, error = null)
            try {
                val items = getSciFiMovies(page)
                _sciFi.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _sciFi.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    private fun loadBollywoodAction(page: Int = 1) {
        viewModelScope.launch {
            _bollywoodAction.value = _bollywoodAction.value.copy(isLoading = true, error = null)
            try {
                val items = getBollywoodActionMovies(page)
                _bollywoodAction.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _bollywoodAction.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadCrimeMystery(page: Int = 1) {
        viewModelScope.launch {
            _crimeMystery.value = _crimeMystery.value.copy(isLoading = true, error = null)
            try {
                val items = getCrimeMysteryMovies(page)
                _crimeMystery.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _crimeMystery.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadHiddenGemsMovies(page: Int = 1) {
        viewModelScope.launch {
            _hideenGemsMovies.value = _hideenGemsMovies.value.copy(isLoading = true, error = null)
            try {
                val items = getHiddenGemsMovies(page)
                _hideenGemsMovies.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _hideenGemsMovies.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadDarkThrillerTv(page: Int = 1) {
        viewModelScope.launch {
            _darkThrillerTv.value = _darkThrillerTv.value.copy(isLoading = true, error = null)
            try {
                val items = getDarkThrillerTv(page)
                _darkThrillerTv.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _darkThrillerTv.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadAnimeTvShows(page: Int = 1) {
        viewModelScope.launch {
            _animeTvShows.value = _animeTvShows.value.copy(isLoading = true, error = null)
            try {
                val items = getAnimeTvShows(page)
                _animeTvShows.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _animeTvShows.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadComfortTvShows(page: Int = 1) {
        viewModelScope.launch {
            _comfortTvShows.value = _comfortTvShows.value.copy(isLoading = true, error = null)
            try {
                val items = getComfortTvShows(page)
                _comfortTvShows.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _comfortTvShows.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadKDramaTvShows(page: Int = 1) {
        viewModelScope.launch {
            _kDramaTvShows.value = _kDramaTvShows.value.copy(isLoading = true, error = null)
            try {
                val items = getKDramaTvShows(page)
                _kDramaTvShows.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _kDramaTvShows.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }




}