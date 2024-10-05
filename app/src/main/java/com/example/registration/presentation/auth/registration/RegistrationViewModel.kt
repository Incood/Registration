package com.example.registration.presentation.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.data.models.User
import com.example.registration.data.models.UserDao
import com.example.registration.domain.repository.SharedPrefRepository
import com.example.registration.presentation.auth.registration.view_models.RegistrationEvent
import com.example.registration.presentation.auth.registration.view_models.RegistrationState
import com.example.registration.presentation.utils.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userDao: UserDao,
    private val sharedPrefRepository: SharedPrefRepository
) :
    ViewModel(), EventHandler<RegistrationEvent> {
    val state : StateFlow<RegistrationState>
        get() = _state
    private val _state = MutableStateFlow(RegistrationState.Content())

    override fun obtainEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.SignUp -> with(_state.value) {
                if (
                    userName.isNotEmpty() &&
                    password.isNotEmpty() &&
                    dateOfBirth.isNotEmpty()
                    )
                    saveUser(
                        name = userName,
                        password = password,
                        dateOfBirth = dateOfBirth,
                        avatarUri = avatarUri

                ) else _state.update { currentState ->
                    currentState.copy(
                        errorUsername = userName.isEmpty(),
                        errorPassword = password.isEmpty()
                    )
                }
            }
            is RegistrationEvent.ChangeUserName -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        userName = userNameTextFieldValue,
                        errorUsername = false,
                        errorPassword = false
                    )
                }
            }
            is RegistrationEvent.ChangePassword -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        password = passwordTextFieldValue,
                        errorUsername = false,
                        errorPassword = false
                    )
                }
            }
            is RegistrationEvent.ChangeDate -> with(event) {
                _state.update { currentState ->
                    currentState.copy(
                        dateOfBirth = dateTextFieldValue,
                        errorUsername = false,
                        errorPassword = false
                    )
                }
            }
            is RegistrationEvent.ChangeAvatarUri -> {
                _state.update { it.copy(avatarUri = event.uri) }
            }
            is RegistrationEvent.ResetAuthorization -> {
                _state.update { currentState ->
                    currentState.copy(successRegistration = false)
                }
            }
            else -> {}
        }
    }

    private fun saveUser(name: String, password: String, dateOfBirth: String, avatarUri: String?) {
        val user = User(name = name, password = password, dateOfBirth = dateOfBirth, avatarUri = avatarUri)
        viewModelScope.launch {
            try {
                val userId = userDao.insertUser(user)
                _state.update { currentState ->
                    currentState.copy(successRegistration = true)
                }
                sharedPrefRepository.saveToken(true)
                sharedPrefRepository.saveCurrentUserId(user.id)
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(successRegistration = false)
                }
            }
        }
    }
}
