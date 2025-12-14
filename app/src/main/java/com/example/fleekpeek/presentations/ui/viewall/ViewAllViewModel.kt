package com.example.fleekpeek.presentations.ui.viewall

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fleekpeek.remote.model.TMDBItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class ViewAllUiState<T>(
    val items: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ViewAllViewModel @Inject constructor(): ViewModel() {

    private  val _viewAllData= MutableStateFlow(ViewAllUiState<List<TMDBItem>>(isLoading = true))
    val viewAllData= _viewAllData.asStateFlow()

    fun onEvent(event: ViewAllEvent){
        when(event){
            is ViewAllEvent.getViewMoreData -> {
                _viewAllData.value= _viewAllData.value.copy(isLoading =  true, error = null)
                _viewAllData.value= _viewAllData.value.copy(isLoading = false, items = event.list, error = null)
            }
        }
    }

}

sealed class ViewAllEvent{
    data class  getViewMoreData(val list: List<TMDBItem>): ViewAllEvent()
}
