package com.formation.myecommerceapp.domain.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.mapper.toCart
import com.formation.myecommerceapp.ui.cart.state.CartState
import com.formation.myecommerceapp.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val productDao: ProductDao,
) : ViewModel() {
    private val _state = MutableStateFlow<Result<CartState>>(Result.Loading)
    val state: StateFlow<Result<CartState>>
        get() = _state.asStateFlow()

    init {
        loadState()
    }

    private fun loadState() {
        // On ouvre une coroutine pour écouter (collecter) le Flow de Room
        viewModelScope.launch {
            productDao.getAllInCart().collect { productEntities ->
                val cart = productEntities.toCart()
                _state.value = Result.Success(CartState(cart = cart))
            }
        }
    }

    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch {
            productDao.getById(productId)?.let { currentProductValue ->
                val newProductValue = currentProductValue.copy(isInCart = false)

                productDao.upsert(newProductValue)

                loadState()
            }
            }
    }
}