package com.example.fleekpeek.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.AppEntryUseCase
import com.example.fleekpeek.presentations.ui.components.OnIntroScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class IntroPageViewModel @Inject constructor(private val appEntryUseCase: AppEntryUseCase):ViewModel() {

    fun onEvent(event: OnIntroScreenEvents){

        when(event){
            OnIntroScreenEvents.SaveAppEntry -> {
                saveAppEntry()
            }
        }
    }


    private fun saveAppEntry() {
        viewModelScope.launch {
            appEntryUseCase.saveEntry()
        }
    }

}