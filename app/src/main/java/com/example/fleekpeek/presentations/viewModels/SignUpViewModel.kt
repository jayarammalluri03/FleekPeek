package com.example.fleekpeek.presentations.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fleekpeek.domain.use_cases.app_entry_useCase.AppEntryUseCase
import com.example.fleekpeek.presentations.ui.login.LoginEvent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(var appEntry: AppEntryUseCase): ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.LoginCredentials ->{
                saveLogin(event.email, event.password)
            }
            else -> {}
        }

    }

    private fun saveLogin(mail: String, password: String) {
        if (mail.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            auth.createUserWithEmailAndPassword(mail, password).addOnSuccessListener {
                Log.e("loginstatus", "success")
                _authState.value = AuthState.Success
                viewModelScope.launch {
                    appEntry.saveLogin(isLogin = true)
                    appEntry.saveEntry()
                }
            }.addOnFailureListener {
                Log.e("loginstatus", "failure")
                _authState.value = AuthState.Error(it.message.toString())
            }
        }

    }


}