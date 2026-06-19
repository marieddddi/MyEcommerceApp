package com.formation.myecommerceapp.domain.ui.productlist

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

class ProductListViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<Result<ProductListState>>(Result.Loading)
    val state: StateFlow<Result<ProductListState>> = _state.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                productRepository.getAll().collect { entities ->
                    _state.value = Result.Success(ProductListState(entities.map { it.toProduct() }))
                }
            } catch (e: Exception) {
                _state.value = Result.Error(e)
            }
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            try {
                val entity = productRepository.getById(product.id)
                if (entity != null) {
                    val updatedEntity = entity.copy(isFavorite = entity.isFavorite)
                    productRepository.upsert(updatedEntity)
                }
            } catch (e: Exception) {
                // Error handling
            }
        }
    }
}
