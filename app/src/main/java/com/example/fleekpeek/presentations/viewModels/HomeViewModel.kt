// presentation/home/HomeViewModel.kt
package com.example.fleekpeek.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetPopularMovies
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetPopularTv
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetTrending
import com.example.fleekpeek.domain.use_cases.responseUsecases.GetUpcomingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrending: GetTrending,
    private val getPopularMovies: GetPopularMovies,
    private val getPopularTv: GetPopularTv,
    private val getUpcomingMovies: GetUpcomingMovies
) : ViewModel() {

    private val _trending = MutableStateFlow(SectionUiState(isLoading = true))
    val trending: StateFlow<SectionUiState> = _trending

    private val _popularMovies = MutableStateFlow(SectionUiState(isLoading = true))
    val popularMovies: StateFlow<SectionUiState> = _popularMovies

    private val _popularTv = MutableStateFlow(SectionUiState(isLoading = true))
    val popularTv: StateFlow<SectionUiState> = _popularTv

    private val _upcoming = MutableStateFlow(SectionUiState(isLoading = true))
    val upcoming: StateFlow<SectionUiState> = _upcoming

    init {
        loadAll()
    }

    private fun loadAll() {
        loadTrending()
        loadPopularMovies()
        loadPopularTv()
        loadUpcoming()
    }

    fun loadTrending(page: Int = 1) {
        viewModelScope.launch {
            _trending.value = _trending.value.copy(isLoading = true, error = null)
            try {
                val items = getTrending(page)
                _trending.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _trending.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadPopularMovies(page: Int = 1) {
        viewModelScope.launch {
            _popularMovies.value = _popularMovies.value.copy(isLoading = true, error = null)
            try {
                val items = getPopularMovies(page)
                _popularMovies.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _popularMovies.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadPopularTv(page: Int = 1) {
        viewModelScope.launch {
            _popularTv.value = _popularTv.value.copy(isLoading = true, error = null)
            try {
                val items = getPopularTv(page)
                _popularTv.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _popularTv.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }

    fun loadUpcoming(page: Int = 1) {
        viewModelScope.launch {
            _upcoming.value = _upcoming.value.copy(isLoading = true, error = null)
            try {
                val items = getUpcomingMovies(page)
                _upcoming.value = SectionUiState(items = items, isLoading = false)
            } catch (e: Exception) {
                _upcoming.value = SectionUiState(items = emptyList(), isLoading = false, error = e.message)
            }
        }
    }
}
