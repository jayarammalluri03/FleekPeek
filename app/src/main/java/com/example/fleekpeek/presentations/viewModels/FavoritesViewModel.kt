package com.example.fleekpeek.presentations.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.Utils.SectionUiState
import com.example.fleekpeek.domain.use_cases.favorites_useCases.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _favoritesState = MutableStateFlow(SectionUiState(isLoading = true))
    val favoritesState: StateFlow<SectionUiState> = _favoritesState

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase().collect { list ->
                _favoritesState.value = SectionUiState(
                    items = list,
                    isLoading = false,
                    error = null
                )
            }
        }
    }
}
