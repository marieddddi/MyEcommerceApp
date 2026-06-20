package com.formation.myecommerceapp.domain.ui.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import com.formation.myecommerceapp.domain.data.repository.ProductLocalFirstRepository
import com.formation.myecommerceapp.domain.domain.mapper.toProductDetails
import com.formation.myecommerceapp.domain.ui.wishlist.state.WishlistState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val productRepository: ProductLocalFirstRepository,
) : ViewModel() {

    val state: StateFlow<WishlistState> = productRepository.getFavoriteProducts()
        .map { products -> WishlistState(products = products.map { it.toProductDetails() }) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WishlistState(emptyList())
        )

    fun toggleFavoriteStatus(product: ProductEntity) {
        viewModelScope.launch {
            productRepository.upsert(product.copy(isFavorite = !product.isFavorite))
        }
    }
}