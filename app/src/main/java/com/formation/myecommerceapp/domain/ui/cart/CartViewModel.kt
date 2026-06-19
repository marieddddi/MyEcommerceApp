package com.formation.myecommerceapp.domain.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.mapper.toCart
import com.formation.myecommerceapp.domain.ui.cart.state.CartState
import com.formation.myecommerceapp.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val productDao: ProductDao,
) : ViewModel() {

    private val _state = MutableStateFlow<Result<CartState>>(Result.Loading)
    val state: StateFlow<Result<CartState>> = _state.asStateFlow()

    init {
        observeCart()
    }

    // Grâce au .collect sur le Flow de Room, l'UI se mettra à jour toute seule lors des changements
    private fun observeCart() {
        viewModelScope.launch {
            productDao.getCartProducts().collect { productEntities ->
                val cart = productEntities.toCart()
                _state.value = Result.Success(CartState(cart = cart))
            }
        }
    }

    // Action pour le bouton "+" dans le panier
    fun increaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            productDao.getById(productId)?.let { currentProduct ->
                val updatedProduct = currentProduct.copy(
                    quantityInCart = currentProduct.quantityInCart + 1
                )
                productDao.upsert(updatedProduct)
            }
        }
    }

    // Action pour le bouton "-" dans le panier
    fun decreaseProductQuantity(productId: Int) {
        viewModelScope.launch {
            productDao.getById(productId)?.let { currentProduct ->
                if (currentProduct.quantityInCart > 0) {
                    val updatedProduct = currentProduct.copy(
                        quantityInCart = currentProduct.quantityInCart - 1
                    )
                    productDao.upsert(updatedProduct)
                }
            }
        }
    }
}