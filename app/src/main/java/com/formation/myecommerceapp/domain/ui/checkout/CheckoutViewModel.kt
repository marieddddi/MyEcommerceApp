package com.formation.myecommerceapp.domain.ui.checkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.remote.dto.EmailRequest
import com.formation.myecommerceapp.domain.data.remote.service.EmailApiService
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
import com.formation.myecommerceapp.domain.domain.mapper.toProductInCart
import com.formation.myecommerceapp.domain.ui.cart.state.ProductInCart
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val productRepository: ProductRepository,
    private val emailApiService: EmailApiService
) : ViewModel() {

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set

    var isProcessing by mutableStateOf(false)
        private set
    var orderSuccess by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onFirstNameChange(newValue: String) { firstName = newValue }
    fun onLastNameChange(newValue: String) { lastName = newValue }
    fun onAddressChange(newValue: String) { address = newValue }
    fun onPhoneChange(newValue: String) { phone = newValue }

    fun validateAndSubmit(onSuccess: () -> Unit) {
        if (firstName.isBlank() || lastName.isBlank() || address.isBlank() || phone.isBlank()) {
            errorMessage = "Veuillez remplir tous les champs"
            return
        }

        if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
            errorMessage = "Format de téléphone invalide"
            return
        }

        viewModelScope.launch {
            isProcessing = true
            errorMessage = null
            try {
                val cartProducts = productRepository.getAll().first()
                    .filter { it.quantityInCart > 0 }
                    .map { it.toProductInCart() }

                if (cartProducts.isEmpty()) {
                    errorMessage = "Votre panier est vide"
                    return@launch
                }

                val orderSummary = cartProducts.joinToString("<br>") { 
                    "${it.quantity}x ${it.name} - ${it.price * it.quantity}€" 
                }
                val totalPrice = cartProducts.sumOf { it.price * it.quantity }

                val htmlContent = """
                    <h1>Merci pour votre commande, $firstName !</h1>
                    <p>Voici le récapitulatif de votre commande à livrer au <b>$address</b> :</p>
                    <p>$orderSummary</p>
                    <hr>
                    <p><b>Total : $totalPrice €</b></p>
                """.trimIndent()

                val response = emailApiService.sendEmail(
                    apiKey = "Bearer ${EmailConfig.API_KEY}",
                    request = EmailRequest(
                        from = "onboarding@resend.dev",
                        to = EmailConfig.TEST_DESTINATION_EMAIL,
                        subject = "Récapitulatif de votre commande #12345",
                        html = htmlContent
                    )
                )

                if (response.isSuccessful) {
                    orderSuccess = true
                    onSuccess()
                } else {
                    errorMessage = "Erreur lors de l'envoi du mail : ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Erreur : ${e.message}"
            } finally {
                isProcessing = false
            }
        }
    }
}
