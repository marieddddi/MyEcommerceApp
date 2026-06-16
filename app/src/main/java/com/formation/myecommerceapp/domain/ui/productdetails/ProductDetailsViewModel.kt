package com.formation.myecommerceapp.domain.ui.productdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
import com.formation.myecommerceapp.domain.domain.exception.ProductNotFoundException
import com.formation.myecommerceapp.domain.domain.mapper.toEntity
import com.formation.myecommerceapp.domain.domain.mapper.toProductDetails
import com.formation.myecommerceapp.ui.productdetails.state.ProductDetailsState
import com.formation.myecommerceapp.ui.routing.ProductDetailsRoute
import com.formation.myecommerceapp.utils.Result
import com.formation.myecommerceapp.utils.getOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ProductDetailsRoute>()
    private val _state = MutableStateFlow<Result<ProductDetailsState>>(Result.Loading)
    val state: StateFlow<Result<ProductDetailsState>> = _state.asStateFlow()

    init {
        loadState()
    }

    private fun loadState() {
        viewModelScope.launch {
            val result = productRepository.getById(route.productId)
                ?.toProductDetails()
                ?.let { Result.Success(ProductDetailsState(product = it)) }
                ?: Result.Error(ProductNotFoundException(route.productId))
            _state.value = result
        }
    }
    // Augmente la quantité de 1
    fun addProductToCart() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val currentEntity = state.product.toEntity()
                val newQuantity = currentEntity.quantityInCart + 1

                productRepository.upsert(
                    currentEntity.copy(quantityInCart = newQuantity)
                )
                loadState() // Recharge l'UI pour afficher le nouveau chiffre
            }
        }
    }

    // Diminue la quantité de 1 (sans descendre en dessous de 0)
    fun removeProductFromCart() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val currentEntity = state.product.toEntity()
                if (currentEntity.quantityInCart > 0) {
                    val newQuantity = currentEntity.quantityInCart - 1

                    productRepository.upsert(
                        currentEntity.copy(quantityInCart = newQuantity)
                    )
                    loadState() // Recharge l'UI
                }
            }
        }
    }

    fun toggleFavoriteStatus() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val product = state.product
                productRepository.upsert(product.toEntity().copy(isFavorite = !product.isFavorite))
                loadState()
            }
        }
    }
}