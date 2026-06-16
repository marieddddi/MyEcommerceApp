package com.formation.myecommerceapp.domain.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
import com.formation.myecommerceapp.domain.domain.mapper.toProduct
import com.formation.myecommerceapp.ui.productlist.state.ProductListState
import com.formation.myecommerceapp.utils.Result
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
}