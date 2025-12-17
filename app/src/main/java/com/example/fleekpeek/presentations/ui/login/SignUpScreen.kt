package com.example.fleekpeek.presentations.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fleekpeek.R
import com.example.fleekpeek.presentations.ui.login.LoginEvent
import com.example.fleekpeek.presentations.viewModels.AuthState
import com.example.fleekpeek.presentations.viewModels.SignUpViewModel

@Composable
fun SignUpScreen(viewModel: SignUpViewModel, succesSignUp: () -> Unit ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var authStatus = viewModel.authState.collectAsState()

    LaunchedEffect(authStatus.value) {
        when (authStatus.value) {
            is AuthState.Success -> {
                succesSignUp()
            }
            is AuthState.Error -> {
                error = (authStatus.value as AuthState.Error).message
            }
            AuthState.Idle -> {}
            AuthState.Loading -> {}
        }


    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 🔵 Logo
            Image(
                painter = painterResource(id = R.drawable.fleekpeek_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 📧 Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 Confirm Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🆕 Sign Up Button
            Button(
                onClick = {
                    if (password != confirmPassword) {
                        error = "Passwords do not match"
                    } else {
                        error = null
                        viewModel.onEvent(LoginEvent.LoginCredentials(email, password))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Sign Up", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
