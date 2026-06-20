package com.formation.myecommerceapp.domain.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
import com.formation.myecommerceapp.domain.domain.mapper.toProduct
import com.formation.myecommerceapp.domain.ui.productlist.state.Product
import com.formation.myecommerceapp.domain.ui.productlist.state.ProductListState
import com.formation.myecommerceapp.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<Result<ProductListState>>(Result.Loading)
    val state: StateFlow<Result<ProductListState>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                // On écoute uniquement les favoris
                productRepository.getFavorites().collect { entities ->
                    _state.value = Result.Success(ProductListState(entities.map { it.toProduct() }))
                }
            } catch (e: Exception) {
                _state.value = Result.Error(e)
            }
        }
    }

    // Version optimisée grâce à la fonction de ton IDE !
    fun removeFromFavorites(product: Product) {
        viewModelScope.launch {
            try {
                // On force directement l'état à "false" en une seule ligne SQL
                productRepository.updateFavoriteStatus(product.id, false)
            } catch (e: Exception) {
                // Gérer l'erreur si nécessaire
            }
        }
    }
}