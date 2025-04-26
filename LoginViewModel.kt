package com.manutencao.colheitadeiras.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manutencao.colheitadeiras.data.model.Usuario
import com.manutencao.colheitadeiras.data.repository.UsuarioRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {
    
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    
    private val _currentUser = MutableLiveData<Usuario?>()
    val currentUser: LiveData<Usuario?> = _currentUser
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.authenticate(username, password)
            if (user != null) {
                _currentUser.value = user
                _loginResult.value = LoginResult(success = user)
            } else {
                _loginResult.value = LoginResult(error = "Usuário ou senha inválidos")
            }
        }
    }
    
    fun logout() {
        _currentUser.value = null
    }
    
    data class LoginResult(
        val success: Usuario? = null,
        val error: String? = null
    )
}
