package com.formation.myecommerceapp.domain.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.local.dao.UserDao
import com.formation.myecommerceapp.domain.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userDao: UserDao
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var statusMessage by mutableStateOf<String?>(null)
        private set

    var isError by mutableStateOf(false)
        private set

    fun onEmailChange(newValue: String) = run { email = newValue; statusMessage = null }
    fun onPasswordChange(newValue: String) = run { password = newValue; statusMessage = null }

    // Fonction 1 : Connexion
    fun login(onSuccess: () -> Unit) {
        if (fieldsAreInvalid()) return

        viewModelScope.launch {
            isLoading = true
            isError = false

            val user = userDao.getUserByEmail(email)

            if (user != null && user.password == password) {
                isLoading = false
                onSuccess()
            } else {
                isLoading = false
                isError = true
                statusMessage = "Identifiants incorrects."
            }
        }
    }

    // Fonction 2 : Inscription locale
    fun register() {
        if (fieldsAreInvalid()) return

        viewModelScope.launch {
            isLoading = true
            isError = false
            try {
                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {
                    isError = true
                    statusMessage = "Cet email est déjà utilisé."
                } else {
                    userDao.registerUser(UserEntity(email, password))
                    isError = false
                    statusMessage = "Compte créé avec succès ! Connecte-toi."
                }
            } catch (e: Exception) {
                isError = true
                statusMessage = "Erreur lors de la création du compte."
            } finally {
                isLoading = false
            }
        }
    }

    private fun fieldsAreInvalid(): Boolean {
        if (email.isBlank() || password.isBlank()) {
            isError = true
            statusMessage = "Veuillez remplir tous les champs"
            return true
        }
        return false
    }
}