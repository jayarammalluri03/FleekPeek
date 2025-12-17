package com.example.fleekpeek.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.AppEntryUseCase
import com.example.fleekpeek.domain.use_cases.favorites_useCases.ClearFavorites
import com.example.fleekpeek.domain.use_cases.favorites_useCases.GetFavoritesUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appEntryUseCase: AppEntryUseCase,
    private val clearFavorites: ClearFavorites
) : ViewModel() {

    fun signOut(onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance().signOut()
            appEntryUseCase.saveLogin(false)
            clearFavorites()
            onComplete()
        }
    }


}