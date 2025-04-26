package com.manutencao.colheitadeiras.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.manutencao.colheitadeiras.R
import com.manutencao.colheitadeiras.data.database.AppDatabase
import com.manutencao.colheitadeiras.data.repository.UsuarioRepository
import com.manutencao.colheitadeiras.ui.viewmodel.LoginViewModel
import com.manutencao.colheitadeiras.ui.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    
    private lateinit var viewModel: LoginViewModel
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var errorTextView: TextView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Inicializar componentes da UI
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        errorTextView = findViewById(R.id.errorTextView)
        
        // Inicializar ViewModel
        val database = AppDatabase.getDatabase(application)
        val repository = UsuarioRepository(database.usuarioDao())
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        
        // Configurar observadores
        viewModel.loginResult.observe(this) { result ->
            if (result.success != null) {
                // Navegar para a tela principal
                startMainActivity()
            } else if (result.error != null) {
                // Mostrar erro
                showError(result.error)
            }
        }
        
        // Configurar listeners
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                showError(getString(R.string.erro_login))
            }
        }
    }
    
    private fun startMainActivity() {
        // Intent para a MainActivity
        // val intent = Intent(this, MainActivity::class.java)
        // startActivity(intent)
        // finish()
    }
    
    private fun showError(errorMessage: String) {
        errorTextView.text = errorMessage
        errorTextView.visibility = View.VISIBLE
    }
}
