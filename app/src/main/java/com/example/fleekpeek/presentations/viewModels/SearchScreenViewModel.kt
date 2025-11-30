package com.example.fleekpeek.presentations.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fleekpeek.domain.use_cases.responseUsecases.TMDBUseCase
import com.example.fleekpeek.presentations.ui.search.SearchEvent
import com.example.fleekpeek.presentations.ui.search.SearchState
import com.example.fleekpeek.remote.model.TMDBItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val useCase: TMDBUseCase): ViewModel() {


    private val _state= mutableStateOf(SearchState())
    val state= _state

    private val _queryFlow= MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val searchResults: StateFlow<PagingData<TMDBItem>> =
        _queryFlow.debounce(400).distinctUntilChanged().flatMapLatest {
            if(it.isBlank())
                flowOf(PagingData.empty())
            else
                useCase.getSearchScreenUseCase(query = it)
        }.cachedIn(viewModelScope).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000)
            , initialValue = PagingData.empty()
        )

     fun onEvent(event: SearchEvent){

        when(event){
            SearchEvent.searchitems -> {
                _queryFlow.value = _state.value.searchQuery.trim()
            }
            is SearchEvent.updateSeachQuery -> {
                _state.value = _state.value.copy(searchQuery = event.searchQuery)
                _queryFlow.value = event.searchQuery
            }
            SearchEvent.ClearSearch -> {
                _state.value = _state.value.copy(searchQuery = "")
                _queryFlow.value = ""
            }
        }
    }


}