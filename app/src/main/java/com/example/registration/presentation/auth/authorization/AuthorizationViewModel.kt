package com.example.registration.presentation.auth.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.EventHandler
import com.example.registration.data.UserDao
import com.example.registration.domain.repository.SharedPrefRepository
import com.example.registration.presentation.auth.authorization.view_models.AuthorizationEvent
import com.example.registration.presentation.auth.authorization.view_models.AuthorizationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val userDao: UserDao,
    private val sharedPrefRepository: SharedPrefRepository
) :
    ViewModel(), EventHandler<AuthorizationEvent> {
    val state: StateFlow<AuthorizationState>
        get() = _state
    private val _state = MutableStateFlow(AuthorizationState.Content())
    override fun obtainEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.Login -> with(_state.value) {
                if (userName.isNotEmpty() && password.isNotEmpty())
                    login(
                        userName = userName,
                        password = password,
                        onSuccess = {
                            _state.update { currentState ->
                            currentState.copy(successAuthorization = true) }
                                    }
                ) else _state.update { currentState ->
                    currentState.copy(
                        errorUserName = userName.isEmpty(),
                        errorPassword = password.isEmpty()
                    )
                }
            }

            is AuthorizationEvent.ChangeUserName -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        userName = userNameTextFieldValue,
                        errorUserName = false,
                        errorPassword = false
                    )
                }
            }

            is AuthorizationEvent.ChangePassword -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        password = passwordTextFieldValue,
                        errorUserName = false,
                        errorPassword = false
                    )
                }
            }
            is AuthorizationEvent.ResetAuthorization -> {
                _state.update { currentState ->
                    currentState.copy(successAuthorization = false)
                }
            }
            else -> {}
        }
    }

    private fun login(userName: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByCredentials(userName, password)
            if (user != null) {
                onSuccess()
                sharedPrefRepository.saveToken(true)
            }
        }
    }
}
