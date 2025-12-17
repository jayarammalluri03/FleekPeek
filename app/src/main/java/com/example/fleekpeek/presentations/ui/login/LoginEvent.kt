package com.example.fleekpeek.presentations.ui.login

sealed  class LoginEvent {

    data class LoginCredentials(val email: String, val password: String): LoginEvent()
}