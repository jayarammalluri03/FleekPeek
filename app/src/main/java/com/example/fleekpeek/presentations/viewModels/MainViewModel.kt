package com.example.fleekpeek.presentations.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.AppEntryUseCase
import com.example.fleekpeek.presentations.NavGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appEntryUseCase: AppEntryUseCase): ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        viewModelScope.launch {
            appEntryUseCase.readEntry().onEach {  entered ->
                startDestination = if (entered) Route.FleekPeekNavigation.route else Route.AppStartNavigation.route
                delay(200)
                splashCondition = false
            }.launchIn(viewModelScope)
        }
    }
}